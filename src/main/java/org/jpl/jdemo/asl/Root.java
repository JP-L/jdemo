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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jpl.jdemo.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Root class
 */
public class Root implements Serializable {
	/**
	   * java.io.Serializable with a non changing serialVersionUID
	   * will automatically handle backwards compatibility
	   * if you add new non transient fields the the class.
	   */
	private static final long serialVersionUID = -525732566482856502L;
	/** The in-memory database. */
	private Map<UUID, User> users = new HashMap<>();
	/** The logger for logging errors. */
	private final transient Logger logger = LoggerFactory.getLogger(Root.class);

	/**
	 * Default constructor.
	 */
	public Root() {
		super();
	}
	
	/**
	 * Retrieve all registered users.
	 * @return a map of users
	 */
	public Map<UUID, User> getUsers() {
		return users;
	}
	
	/**
	 * 
	 * @param users
	 */
	public void setUsers(Map<UUID, User> users) {
	    this.users = users;
	}
	
	/**
	 * Dump all registered users in the logfile.
	 */
	public final void dumpUsersToLog() {
		logger.info("Dumping all users...");
		for (User u : users.values()) {
			logger.info(u.toString());
			logger.info(u.toString());
		}
	}

}
