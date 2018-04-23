
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService	customerService;


	/**
	 * Tests the creation and saving of new Customers.
	 * <p>
	 * Functional Requirement 21.1: An actor who is not authenticated must be able to: Register to the system as a customer.
	 * <p>
	 * Case 1: An actor enters both valid username and password. No exception is expected. <br>
	 * Case 2: An actor enters an invalid username. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor enters an invalid password. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateAndSaveCustomer() {
		final Object testingData[][] = {
			{
				"testname", "testpass", null
			}, {
				null, "testpass", IllegalArgumentException.class
			}, {
				"testname", null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation and saving of a new Customer.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new Customers in the system.
	 * 
	 * @param username
	 *            The username that will be used to create the new Customer.
	 * @param password
	 *            The password that will be used to create the new Customer.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateAndSave(final String username, final String password, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Customer test = this.customerService.create();
			final UserAccount ua = test.getUserAccount();
			ua.setUsername(username);
			ua.setPassword(password);
			test.setUserAccount(ua);
			test.setName("test");
			test.setSurname("test");
			test.setEmail("test@test.com");
			test.setPhone("");
			test.setAddress("");
			final Customer res = this.customerService.save(test);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.customerService.findOne(res.getId())));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
