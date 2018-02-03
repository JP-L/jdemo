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

import org.jpl.jdemo.to.TransferObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import static org.junit.Assert.*;


/**
 * JUnit test for the RegisterUser class.
 * @author limpens
 *
 */
public class RegisterUserTest {
	/** Prevayler persistence storage. */
	private Prevayler<Root> prevayler;
	
	@Before
	public final void init() throws Exception {
		prevayler = PrevaylerFactory.createPrevayler(new Root());
	}
	
	@After
	public final void emptyTheDatabase() throws Exception {
		prevayler.execute(new RemoveAllUser(null));
	}
	
    /**
     * Simple test to validate if a User can be registered.
     */
	@Test
    public final void canIRegisterAUser() throws Exception {
		final TransferObject to = new TransferObject("Larry", 
													 "Larry", 
													 "larry@test.org",
													 "somePassword");
		
        assertNotNull(prevayler.execute(new RegisterUser(to)));
    }
}
