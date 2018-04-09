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
package org.jpl.jdemo;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.remote.*;

/**
 * Selenium test for jdemo.
 * @author limpens
 *
 */
public class ChromeTests extends AbstractTest {
	/**
	 * @see org.jpl.jdemo.AbstractTest#configure()
	 */
	public DesiredCapabilities configure() {
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "WIN10");
		capabilities.setCapability("version", "65");
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("name", "Chrome - Selenium Testing");
		return capabilities;
	}

    /**
     * Simple test to validate if the web application is available.
     */
	@Test
    public final void isAvailable() throws Exception {
		this.getDriver().get(URL_TO_TEST);
		assertEquals("jdemo", this.getDriver().getTitle());
    }
}
