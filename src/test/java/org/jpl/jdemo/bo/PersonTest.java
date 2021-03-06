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

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 * JUnit test for the Person class.
 * @author limpens
 *
 */
public class PersonTest {
    /**
     * Simple test to validate if a Person object can be created.
     */
	@Test
    public final void canIConstructAPersonWithANameAndEmail() {
        Person person = new Person();
        person.setFirstName("Larry");
        person.setEmail("larry@test.org");
        person.setLastName("Larry");
        assertEquals("Larry", person.getFirstName());
    }
	
	/**
	 * Test to verify if the Person object can produce human readable output
	 */
	@Test
	public final void doIGetHumanReadableOutput() {
		Person person = new Person();
        person.setFirstName("Larry");
        person.setEmail("larry@test.org");
        person.setLastName("Larry");
        assertThat(person.toString(), containsString("firstName,:,Larry,lastName,:,Larry,email,:,larry@test.org"));
	}
}
