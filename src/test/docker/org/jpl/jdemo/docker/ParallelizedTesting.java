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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jpl.jdemo.docker.Stage;
import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;
/**
 * Base class for parallel Selenium tests.
 * @author Testingbot
 *
 */
public class ParallelizedTesting extends Parameterized {

	private static class ThreadPoolScheduler implements RunnerScheduler {
		/** The Release stage which defines how many threads to use. See Stage for all stages. Set through a system property. */
		private static final String RELEASE_STAGE = System.getProperty("stage", "DEVELOPMENT").toUpperCase();
		/** The executor. */
		private ExecutorService executor;

		/**
		 * The Thread pool scheduler which sets the number of parallel threads
		 */
		public ThreadPoolScheduler() {
			final Stage releaseStage = Stage.valueOf(RELEASE_STAGE);
			executor = Executors.newFixedThreadPool(releaseStage.getNumberOfThreads());
		}

		/* 
		 * @see org.junit.runners.model.RunnerScheduler#finished() 
		 */
		@Override
		public void finished() {
			executor.shutdown();
			try {
				executor.awaitTermination(10, TimeUnit.MINUTES);
			} catch (InterruptedException exc) {
				throw new RuntimeException(exc);
			}
		}

		/*
		 * @see org.junit.runners.model.RunnerScheduler#schedule(java.lang.Runnable)
		 */
		@Override
		public void schedule(Runnable childStatement) {
			executor.submit(childStatement);
		}		
	}

	/**
	 * Constructor which sets the thread pool scheduler
	 */
	public ParallelizedTesting(Class<?> klass) throws Throwable {
		super(klass);
		setScheduler(new ThreadPoolScheduler());
	}

}