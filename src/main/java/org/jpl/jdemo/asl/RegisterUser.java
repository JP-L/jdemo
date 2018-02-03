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

import java.io.Serializable;
import java.util.Date;

import org.jpl.jdemo.bo.Person;
import org.jpl.jdemo.bo.User;
import org.jpl.jdemo.to.TransferObject;
import org.prevayler.TransactionWithQuery;

/**
 * Register a user.
 * 
 * @author limpens
 *
 */
public final class RegisterUser implements TransactionWithQuery<Root, User>, Serializable {
	/** A generated serialVersionUID. */
	private static final long serialVersionUID = -375562099287609229L;
	/** the transfered data. */
	private final TransferObject to;
	
	/**
	 * Default constructor
	 * @param to the TransferObject
	 */
	public RegisterUser(final TransferObject to) {
		super();
		this.to = to;
	}
	
	/**
	 * Registering a user
	 * @param prevalentSystem
	 * @param executionTime
	 * @return true when the user is successfully registered, otherwise false
	 * @throws Exception
	 */
	@Override
	public final User executeAndQuery(final Root prevalentSystem, final Date executionTime) 
		throws Exception
	{
		final Person p = new Person();
		p.setFirstName(to.firstName);
		p.setLastName(to.lastName);
		p.setEmail(to.email);
		// Some comment to have a new commit for trigger codecov
		final User u = new User(p, to.password);
			
		prevalentSystem.getUsers().put(u.getId(), u);
		return u;
	}
}
