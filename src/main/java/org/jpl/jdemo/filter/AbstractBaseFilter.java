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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Abstract class
 */
public abstract class AbstractBaseFilter implements Filter {
	/** filter config. */
	private FilterConfig filterConfig;
	
	/**
	 * Default constructor
	 */
	public AbstractBaseFilter() { // NOPMD by limpens on 23-3-08 12:09
		super();
	}
	
	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * {@inheritDoc}
	 */
	@Override
    public void doFilter(final ServletRequest servletRequest, 
    						   final ServletResponse servletResponse, 
    						   final FilterChain filterChain) 
    	throws ServletException, IOException {
    	filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * {@inheritDoc}
     */
	@Override
    public final void init(final FilterConfig aFilterConfig) {
    	setFilterConfig(aFilterConfig);
    }

    /**
     * 
     * @param filterconfig the filter configuration.
     */
    public abstract void setFilterConfig(FilterConfig filterconfig);

    /**
     * 
     * @return FilterConfig
     */
    public final FilterConfig getFilterConfig() { // NOPMD by limpens on 23-3-08 11:49
    	return filterConfig;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     * {@inheritDoc}
     */
    @Override
    public void destroy() { // NOPMD by limpens on 23-3-08 11:49
    }	
}
