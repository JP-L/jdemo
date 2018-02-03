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
package org.jpl.jdemo.controllers;

import javax.faces.bean.ManagedBean;  
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;  
import javax.faces.context.FacesContext;  

import org.jpl.jdemo.sessionfacade.SimpleSessionFacade;
import org.jpl.jdemo.to.TransferObject;

/**
 * The controller class.
 * 
 *
 */
@ManagedBean(name = "user")  
@RequestScoped  
public class UserController {
	/** The Session Facade. */
	@ManagedProperty(value="#{simpleSessionFacade}")
	private SimpleSessionFacade ssf;
	
	/** The person's first name. */
	private String firstName;
	/** The person's last name. */
    private String lastName;
    /** The person's email. */
    private String email; 
    /** The user password. */
    private String password; 
  
    /**
     * The default constructor
     */
    public UserController() {
    	super();
    }
    
    /**
     * Set the SessionFacade object.
     * @param facade the session facade to inject
     */
    public void setSsf(final SimpleSessionFacade facade) {
    	ssf = facade;
    }
    
    /**
     * Get the SessionFacade object.
     * @return the injected session facade object.
     */
    public SimpleSessionFacade getSsf() {
    	return ssf;
    }
  
    public String getFirstName() {  
        return firstName;  
    }  
  
    public void setFirstName(String name) {  
        this.firstName = name;  
    }  
  
    public String getLastName() {  
        return lastName;  
    }  
  
    public void setLastName(String lastName) {  
        this.lastName = lastName;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    /**
     * Register the person as a user.
     * @return "success" when the user registration was successfull, 
     * 		    otherwise "unsuccess"
     */
    public String add() {
    	final TransferObject to = new TransferObject(firstName, 
				 									 lastName,
				 									 email,
				 									 password);
    	
    	return ssf.registerUser(to) ? "success" : "unsuccess";
    }  
    
    /**
     * Login as a user.
     * @return "output" when the user submitted a valid email and password; 
     * 			otherwise "invalid"
     */
    public String login() {
    	final TransferObject to = new TransferObject(firstName, 
    												 lastName, 
    												 email, 
    												 password);
    	final TransferObject result = ssf.validateUser(to);
    	if (result != null) {
    		this.firstName = result.firstName;
    		this.lastName = result.lastName;
    		this.email = result.email;
    	}
    	return (result != null) ? "output" : "invalid";
    }  
  
    /**
     * Logout.
     */
    public void logout() {  
        FacesContext.getCurrentInstance().getExternalContext()
        	.invalidateSession();  
        FacesContext.getCurrentInstance()  
                .getApplication().getNavigationHandler()  
                .handleNavigation(FacesContext.getCurrentInstance(), 
                				  null, 
                				  "/login.xhtml");
    }
}
