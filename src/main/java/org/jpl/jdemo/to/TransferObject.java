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
package org.jpl.jdemo.to;

import java.io.Serializable;

/**
 * A Transfer Object to carry multiple data elements across a tier.
 * @author limpens
 *
 */
public final class TransferObject implements Serializable {
	/** The serialVersionUID. */
	public static final long serialVersionUID = 1L;
	/** The person's first name. */
	public final String firstName;
	/** The person's last name. */
    public final String lastName;
    /** The person's email. */
    public final String email;
    /** The user password. */
    public final String password; 
	
	/**
	 * Default constructor
	 * @param fn firstname
	 * @param ln lastname
	 * @param em email
	 * @param pw password
	 */
	public TransferObject(final String fn, final String ln, final String em, final String pw) {
		super();
		this.firstName = fn;
		this.lastName = ln;
		this.email = em;
		this.password = pw;
	}

}
