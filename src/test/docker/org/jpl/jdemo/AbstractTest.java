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

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

/**
 * Base class for Selenium tests.
 * @author limpens
 *
 */
public abstract class AbstractTest {
	/** . */
	public static final String KEY = System.getProperty("key");
	public static final String SECRET = System.getProperty("secret");
	/** . */
	private static final String RUN_MODE = System.getProperty("runMode").toUpperCase();
	/** . */
	public static final String URL_TO_TEST = System.getProperty("webapp_url");
	
	/** Webdriver. */
	private WebDriver driver;
	
	/**
	 * Set the capabilities to use in the test
	 * @return The capabilities to use in the test
	 */
	public abstract DesiredCapabilities configure();
	
	public WebDriver getDriver() {
		return this.driver;
	}
	
	@Before
	public final void init() throws Exception {        
		DesiredCapabilities desiredCapabilities = configure();
		driver = new RemoteWebDriver(
					RunMode.getUrl(RUN_MODE, KEY, SECRET), 
					desiredCapabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@After
	public final void cleanUp() throws Exception {
		driver.quit();
	}
}
