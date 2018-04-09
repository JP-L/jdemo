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

import java.net.MalformedURLException;
import java.net.URL;

enum RunMode {
	/** Remote and local run mode supported. */
	LOCAL("LOCAL", "@localhost:4445/wd/hub"),
	REMOTE("REMOTE", "@hub.testingbot.com/wd/hub");
	
	/** The run mode name. */
	private final String name;
	/** The url to use. */
	private final String url;
	
	/**
	 * Set the URL
	 * @param url
	 */
	RunMode(final String name, final String url) {
		this.name = name;
        this.url = url;
    }
	
	/**
	 * Return the postfix url
	 * @return url
	 */
	public String getUrlPostfix() {
		return this.url;
	}

	/**
	 * Return the runmode
	 * @return name
	 */
    public static URL getUrl(final String name, final String key, final String secret) 
    	throws MalformedURLException
    {
    	final String prefix = "http://" + key + ":" + secret;
    	final String postfix = (LOCAL.name.equalsIgnoreCase(name)) ? LOCAL.getUrlPostfix() : REMOTE.getUrlPostfix();
    	return new URL(prefix.concat(postfix));
    }
}
