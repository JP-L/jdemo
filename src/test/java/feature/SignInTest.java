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
package feature;

import org.jpl.jdemo.sessionfacade.SimpleSessionFacade;
import org.jpl.jdemo.to.TransferObject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author limpens
 *
 */
public class SignInTest {
	private final SimpleSessionFacade ssf = new SimpleSessionFacade();
 
	@Given("^a member navigates to the mock application$")
	public void navigateToMock() {
		System.out.println("Entering: a member navigates to the mock application");
	}
	
	@When("^the member signs in with '(.+)' and '(.+)'$")
	public void registerAsMember(final String email, final String password) throws Throwable {
		final TransferObject to = new TransferObject(null, null, email, password);
		final TransferObject result = ssf.validateUser(to);
		assertNotNull(result);
	}
 
	@Then("^the member should see he or she logged in '(.+)'")
	public void verifyResult(final String outcome) throws Throwable {
		assertThat("successfully", is(outcome)); //TODO add function to retrieve member count
	}
}