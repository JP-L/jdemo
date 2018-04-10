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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Browser {
	/** Browsers supported. */
	CHROME("chrome", Arrays.asList(60,61,62,63,64,65)),
	FIREFOX("firefox", Arrays.asList(56,57,58,59)),
	INTERNET_EXPLORER("ie", Arrays.asList(10,11)),
	SAFARI("safari", Arrays.asList(10,11));
	
	private final String name;
	private final List<Integer> supportedVersions;
	/**
	 * Constructor
	 * @param name
	 */
	private Browser(final String browser, List<Integer> versions) {
		name = browser;
		supportedVersions = versions;
	}
	/**
	 * Return the name of the user
	 * @return
	 */
	public String getName() {
		return name;
	}
	public String getMinorVersion() {
		Integer minValue = Collections.min(supportedVersions);
		return minValue.toString();
	}
	public String getMajorVersion() {
		Integer maxValue = Collections.max(supportedVersions);
		return maxValue.toString();
	}
	public String getRandomVersion() {
		Collections.shuffle(supportedVersions);
		Integer value = supportedVersions.get(0);
		return value.toString();
	}
}
