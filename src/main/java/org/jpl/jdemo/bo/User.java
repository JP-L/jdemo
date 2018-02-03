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
package org.jpl.jdemo.bo;

/**
 * Business Object to separate business data and logic using an object model.
 * @author limpens
 *
 */
public class User extends AbstractBusinessEntityObject {  
	/** A generated serialVersionUID. */
	private static final long serialVersionUID = -3773650588159000769L;
	/** The real person who registered as a user. */ 
    private final Person person;
    /** The user password (usually this is stored encrypted). */
    private final String password;  
     
    /**
     * Default constructor
     * @param p the person
     * @param pw the password
     */
    public User(final Person p, final String pw) {  
      super();
      this.person = p;
      this.password = (pw != null) ? pw : "";
    }  
  
    /**
     * Retrieve all registered personal information.
     * @return person's information as type Person
     */
    public final Person getPerson() {  
        return person;  
    }
    
    /**
     * Verify if the given email equals the person's email.
     * @param email the email submitted
     * @return true if the email equals the given email, otherwise false
     */
    public final boolean equalsEmail(final String email) {
    	return person.getEmail().equalsIgnoreCase(email);
    }
  
    /**
     * Validate the password.
     * @param given the password submitted
     * @return true if the password equals the given password, otherwise false
     */
    public final boolean validatePassword(final String given) {  
        return password.equalsIgnoreCase(given);
    }
}
