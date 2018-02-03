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

import java.util.List;

import org.jpl.jdemo.sessionfacade.SimpleSessionFacade;
import org.jpl.jdemo.to.TransferObject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author limpens
 *
 */
public class SignUpAsMemberTest {
	private final SimpleSessionFacade ssf = new SimpleSessionFacade();
 
	@Given("^some users navigate to the mock application$")
	public void navigateToMock() {
		System.out.println("Entering: Some users navigate to the mock application");
	}
	
	@When("^some users register as a member with the following data$")
	public void registerAsMember(final List<TransferObject> users) throws Throwable {
		
		for (TransferObject to : users) {
			ssf.registerUser(to);
		}
	}
 
	@Then("^the mock application should count (\\d+) registered users")
	public void getMemberCount(final int expectedCount) throws Throwable {
		assertThat(2, is(expectedCount)); //TODO add function to retrieve member count
	}
}