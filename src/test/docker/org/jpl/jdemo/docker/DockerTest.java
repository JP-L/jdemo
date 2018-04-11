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
 * 
 * Most of this code comes from https://testingbot.com/support/getting-started/parallel-junit.html#clients
 */
package org.jpl.jdemo.docker;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.jpl.jdemo.docker.Browser;
import org.jpl.jdemo.docker.RunMode;
import org.jpl.jdemo.docker.ParallelizedTesting;

/**
 * Test class for parallel Selenium tests.
 * @author Testingbot
 *
 */
@RunWith(ParallelizedTesting.class)
public class DockerTest {
	/** The key and secret to access Testingbot. */
	private static final String KEY = System.getProperty("key");
	private static final String SECRET = System.getProperty("secret");
	/** The run mode which sets Testingbot to use the tunnel or not. */
	private static final String RUN_MODE = System.getProperty("runMode", "LOCAL").toUpperCase();
	/** The URL to test. */
	private static final String URL_TO_TEST = System.getProperty("webapp_url");
	
	/** The platform used for testing. */
	private String platform;
	/** The browser name used for testing. */
	private String browserName;
	/** The browser version used for testing. */
	private String browserVersion;
	/** Webdriver. */
	private WebDriver driver;

	/**
	 * The list of all platforms, browsers and browser versions to use in the tests.
	 * @return the list with all environment settings
	 * @throws Exception
	 */
	@Parameterized.Parameters
	public static LinkedList<String[]> getEnvironments() throws Exception {
		LinkedList<String[]> env = new LinkedList<String[]>();
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.CHROME.getName(), Browser.CHROME.getMajorVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.FIREFOX.getName(), Browser.FIREFOX.getMajorVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.INTERNET_EXPLORER.getName(), Browser.INTERNET_EXPLORER.getMajorVersion()});

		env.add(new String[]{Platform.WINDOWS.toString(), Browser.CHROME.getName(), Browser.CHROME.getMinorVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.FIREFOX.getName(),Browser.FIREFOX.getMinorVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.INTERNET_EXPLORER.getName(), Browser.INTERNET_EXPLORER.getMinorVersion()});
		
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.CHROME.getName(), Browser.CHROME.getRandomVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.FIREFOX.getName(),Browser.FIREFOX.getRandomVersion()});
		env.add(new String[]{Platform.WINDOWS.toString(), Browser.INTERNET_EXPLORER.getName(), Browser.INTERNET_EXPLORER.getRandomVersion()});

		return env;
	}

	/**
	 * Constructor which sets platform, browser nd version to use in the test
	 * @param platform the platform from org.openqa.selenium.Platform
	 * @param browserName the browser name as used by Selenium
	 * @param browserVersion the browser version as used by Selenium
	 */
	public DockerTest(final String platform, final String browserName, final String browserVersion) {
		this.platform = platform;
		this.browserName = browserName;
		this.browserVersion = browserVersion;
	}

	/**
	 * Set up the tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", platform);
		capabilities.setCapability("browser", browserName);
		capabilities.setCapability("browserVersion", browserVersion);
		capabilities.setCapability("name", browserName + " - Selenium Parllel Testing");
		
		driver = new RemoteWebDriver(getUrl(), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Clean up after the tests
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/**
	 * The test to run
	 * @throws Exception
	 */
	@Test
	public void testSimple() throws Exception {
		driver.get(URL_TO_TEST);
		assertThat(driver.getTitle(), startsWith("jdemo"));
		//assertEquals("jdemo", driver.getTitle());		
	}

	/**
	 * Return the url based on the runmode
	 * @return name
	 */
    private URL getUrl() 
    	throws MalformedURLException
    {
    	final StringBuffer url = new StringBuffer("http://")
    								.append(KEY)
    								.append(":")
    								.append(SECRET)
    								.append(RunMode.valueOf(RUN_MODE).getUrlPostfix());
    	return new URL(url.toString());
    }

}
