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
package org.jpl.jdemo.sessionfacade;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jpl.jdemo.asl.RegisterUser;
import org.jpl.jdemo.asl.Root;
import org.jpl.jdemo.asl.ValidateUser;
import org.jpl.jdemo.bo.User;
import org.jpl.jdemo.to.TransferObject;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author limpens
 *
 */
@ManagedBean(name = "simpleSessionFacade", eager=true)  
@ApplicationScoped
public class SimpleSessionFacade {
	/** The logger for logging errors. */
	private final Logger logger = LoggerFactory.getLogger(SimpleSessionFacade.class);
	/** Prevayler persistence storage. */
	private final Prevayler<Root> prevayler;
	
	/**
	 * Constructor
	 */
	public SimpleSessionFacade() {
		final FacesContext ctx = FacesContext.getCurrentInstance();
    	final String prevelanceJournalPath = ctx.getExternalContext().getInitParameter("journal.path");
    	
		try {
			final String journals = prevelanceJournalPath + "/PrevalenceBase_"; 
			prevayler = PrevaylerFactory.createPrevayler(new Root(), journals);
			
		} catch (Exception e) {
			logger.error("Could not create a Prevelance instance.", e);
			throw new SessionFacadeInitializationException("Could not create a Prevelance instance.", e);
		}
	}
	
	/**
	 * Register a user
	 * @param to the TransferObject containing all information of a user
	 * @return truewhen the user successfully was registered
	 */
	public boolean registerUser(final TransferObject to) {
		boolean executionResult = false;
		try {
			User execResponse = prevayler.execute(new RegisterUser(to));
			executionResult = (execResponse != null) ? true : false;
		} catch (Exception e) {
			logger.error("Could not register a user.", e);
		} 
		return executionResult;
	}
	
	/**
	 * Validate the login user.
	 * @param to the TransferObject containing all required information of a user
	 * @return true when the user successfully loggedin, otherwise false
	 */
	public TransferObject validateUser(final TransferObject to) {
		TransferObject result = null;
		try {
			User queryResponse = prevayler.execute(new ValidateUser(to));
			if (queryResponse != null) {
				result = new TransferObject(queryResponse.getPerson().getFirstName(), 
											queryResponse.getPerson().getLastName(), 
											queryResponse.getPerson().getEmail(), 
											to.password);
			}
		} catch (Exception e) {
			logger.error("Some exception", e);
		} 
		return result;
	}
}
