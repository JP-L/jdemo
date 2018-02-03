/*
 * Copyright (c) 2018 JP-L, https://www.jp-l.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.jpl.jdemo.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Use this filter to synchronize requests to your web application and
 * reduce the maximum load that each individual user can put on your
 * web application. Requests will be synchronized per session.  When more
 * than one additional requests are made while a request is in process,
 * only the most recent of the additional requests will actually be
 * processed.
 * <p>
 * If a user makes two requests, A and B, then A will be processed first
 * while B waits.  When A finishes, B will be processed.
 * <p>
 * If a user makes three or more requests (e.g. A, B, and C), then the
 * first will be processed (A), and then after it finishes the last will
 * be processed (C), and any intermediate requests will be skipped (B).
 * <p>
 * There are two additional limitations:
 * <ul>
 *   <li>Requests will be excluded from filtering if their URI matches
 *       one of the exclusion patterns.  There will be no synchronization
 *       performed if a request matches one of those patterns.</li>
 *   <li>Requests wait a maximum of 5 seconds, which can be overridden
 *       per URI pattern in the filter's configuration.</li>
 * </ul>
 * 
 * This is an updated class based upon the code of Kevin Chipalowsky and Ivelin Ivanov
 *    http://www.onjava.com/2004/03/24/examples/RequestControlFilter.java
 * 
 */
public final class InterceptingRequestFilter extends AbstractBaseFilter {
	/** The logger for logging errors. */
	private final Logger logger = LoggerFactory.getLogger(InterceptingRequestFilter.class);
	/** REQUEST IN PROCESS. */
	private static final String REQUEST_IN_PROCESS = "InterceptingRequestFilter.requestInProcess";
	/** REQUEST QUEUE. */
	private static final String REQUEST_QUEUE = "InterceptingRequestFilter.requestQueue";
	/** SYNC OBJECT KEY. */
	private static final String SYNC_OBJECT_KEY = "InterceptingRequestFilter.sessionSync";
	/** DEFAULT DURATION. */
	private static final Long DEFAULT_DURATION = new Long(5000L);
	/** excluded patterns. */
	private LinkedList<String> excludePatterns;
	/** max wait duration. */
	private HashMap<String, Long> maxWaitDurations;
    
	/**
	 * Default constructor.
	 */
	public InterceptingRequestFilter() {
		super();
	}
	
	/**
	 * @see org.jpl.patterns.jee.filter.BaseFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest,
						 ServletResponse servletResponse, 
						 FilterChain filterChain)
		throws ServletException, IOException 
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
  
        if(!isFilteredRequest(request)){
        	logger.info(new StringBuffer("No filtered Request: ").append(request.toString()).toString());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
 
        synchronized(getSynchronizationObject(session)){
        	// if another request is being processed, then wait
        	if(isRequestInProcess(session)){
        		// Put this request in the queue and wait
        		logger.info(new StringBuffer("Queued Request: ").append(request.toString()).toString());
        		enqueuRequest(request);
        		//
        		// Wait for this server to finish with its current request so that it can begin processing our next request. This method also detects if its
        		// request is replaced by another request in the queue.
        		// 
        		// @param request
        		//            Wait for this request to be ready to run
        		// @return true if this request may be processed, or false if this request was replaced by another in the queue.
        		//
        		boolean skipRequest = false;
        		boolean runOnce = false;
	        	while(!runOnce) {
	        		// wait for the currently running request to finish, or until this
	        		// thread has waited the maximum amount of time
	        		try { 
	                	getSynchronizationObject( session ).wait( getMaxWaitTime( request ).longValue() );
	        		} catch( InterruptedException ie ) {
	        			// Restore the interrupted status
	        			Thread.currentThread().interrupt();
	        			break;
	        		}
	        		// This request can be processed now if it hasn't been replaced
	        		// in the queue
	        		skipRequest = request == session.getAttribute(REQUEST_QUEUE);
	        		runOnce=true;
	        	}
	        	
	        	if (skipRequest) {
	        		return ;
	        	}
            }
            //
            logger.debug(new StringBuffer("Current executed Request: ").append(request.toString()).toString());
            setRequestInProgress(request);
        }
 
        //
        try{
           filterChain.doFilter(servletRequest, servletResponse);
        } catch(Exception exception) {
        	logger.warn(
        			new StringBuffer("Exception occured during processing of request ")
        				.append(exception)
        				.append(", destroying session").toString()
        			);
         destroy();
        } finally{
        	logger.debug(new StringBuffer("release queued Request: ").append(request.toString()).toString());
            releaseQueuedRequest(request);
        }
	}

	/**
	 * @see org.jpl.jdemo.filter.AbstractBaseFilter#setFilterConfig(FilterConfig)
	 * {inheritDoc}
	 */
	@Override
	public void setFilterConfig(final FilterConfig filterConfig) {
		final Enumeration<String> enumeration = filterConfig.getInitParameterNames();
		excludePatterns = new LinkedList<>();
		maxWaitDurations = new HashMap<>();
		while(enumeration.hasMoreElements()) {
			String paramName = enumeration.nextElement();
			String paramValue = filterConfig.getInitParameter(paramName);
			if (paramName.startsWith("excludePattern")) {
				excludePatterns.add(paramValue);
			} else if (paramName.startsWith("maxWaitMilliseconds")) {
				String durationString = filterConfig.getInitParameter(paramName);
				Integer endDuration = Integer.valueOf(durationString.indexOf('.'));
				if(endDuration.intValue() != -1) {
					durationString = durationString.substring(0, endDuration.intValue());
				}
				Long duration = new Long(durationString);
				maxWaitDurations.put(paramValue, duration);
			}
		}
	}

	/**
	 * Get a synchronization object for this session
	 * 
	 * @param session
	 *            HttpSession -
	 * @return Object
	 */
    private static synchronized Object getSynchronizationObject(final HttpSession session) {
    	String syncObj = (String) session.getAttribute(SYNC_OBJECT_KEY);
    	if(syncObj == null) {
    		syncObj = SYNC_OBJECT_KEY;
    		session.setAttribute(SYNC_OBJECT_KEY, syncObj);
    	}
    	return syncObj;
    }
    
    /**
	 * Record that a request is in process so that the filter blocks additional requests until this one finishes.
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
    private void setRequestInProgress(final HttpServletRequest request) {
    	final HttpSession session = request.getSession();
    	session.setAttribute(REQUEST_IN_PROCESS, request.toString());
    }

    /**
	 * Release the next waiting request, because the current request has just finished.
	 * 
	 * @param request
	 *            The request that just finished
	 */
    private void releaseQueuedRequest(final HttpServletRequest request) {
    	final HttpSession session = request.getSession();
    	synchronized( getSynchronizationObject(session) ) {
    		if ( session.getAttribute(REQUEST_IN_PROCESS) == request ) {
    			session.removeAttribute(REQUEST_IN_PROCESS);
    			getSynchronizationObject(session).notify();
    		}
    	}
    }

    /**
	 * Is this server currently processing another request for this session?
	 * 
	 * @param session
	 *            The request's session
	 * @return true if the server is handling another request for this session
	 */
    private Boolean isRequestInProcess(final HttpSession session) {
    	return Boolean.valueOf(session.getAttribute(REQUEST_IN_PROCESS) != null);
    }
    
    /**
	 * Put a new request in the queue. This new request will replace any other requests that were waiting.
	 * 
	 * @param request
	 *            The request to queue
	 */
    private void enqueuRequest(final HttpServletRequest request) {
    	final HttpSession session = request.getSession();
    	synchronized( getSynchronizationObject(session) ) {
    		session.setAttribute(REQUEST_QUEUE, request.toString());
        	getSynchronizationObject( session ).notify();
    	}
    }
    
    /**
	 * What is the maximum wait time (in milliseconds) for this request
	 * 
	 * @param request
	 *            HttpServletRequest -
	 * @return Maximum number of milliseconds to hold this request in the queue
	 */
    private Long getMaxWaitTime(final HttpServletRequest request) {
    	final String path = request.getRequestURI();
    	for ( Iterator<String> patternIterator = maxWaitDurations.keySet().iterator(); patternIterator.hasNext(); ) {
    		String p = patternIterator.next();
    		if ( p.equalsIgnoreCase( path ) )
    			return maxWaitDurations.get(p);
    	}
    	return DEFAULT_DURATION;
    }
    
    /**
	 * Look through the filter's configuration, and determine whether or not it should synchronize this request with others.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
    private Boolean isFilteredRequest(final HttpServletRequest request) {
    	final String path = request.getRequestURI();
    	for ( Iterator<String> patternIterator = excludePatterns.iterator(); patternIterator.hasNext(); ) {
    		String p = patternIterator.next();
    		if ( p.regionMatches(true, -1, path, -1, path.length()) )
    			return Boolean.valueOf(false);
    	}
    	return Boolean.valueOf(true);
    }	
}
