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
package org.jpl.jdemo.asl;

import java.util.Date;

import org.jpl.jdemo.bo.User;
import org.jpl.jdemo.to.TransferObject;
import org.prevayler.Query;

/**
 * Validate a user
 * 
 * @author limpens
 *
 */
public final class ValidateUser implements Query<Root, User> {
	/** A generated serialVersionUID. */
	private static final long serialVersionUID = 6361620459702749376L;
	/** the transfered data. */
	private final TransferObject to;
	
	
	/**
	 * Default constructor
	 * @param to the TransferObject
	 */
	public ValidateUser(final TransferObject to) {
		super();
		this.to = to;
	}
	
	/**
	 * Validate the login user.
	 * @param prevalentSystem
	 * @param executionTime
	 * @return the user when the user successfully loggedin, otherwise false
	 * @throws Exception
	 */
	@Override
	public User query(Root prevalentSystem, Date executionTime) 
		throws Exception
	{
		User user = null;
		//Find the user in the list
		for (User u : prevalentSystem.getUsers().values()) {
			if (u.equalsEmail(to.email)) {
				//Verify the password
				user = u.validatePassword(to.password) ? u : null;
				break;
			}
		}
		return user;
	}
}
