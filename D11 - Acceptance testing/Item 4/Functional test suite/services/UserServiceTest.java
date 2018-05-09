
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	@Autowired
	private UserService	userService;


	/**
	 * Tests the register to the system as a user
	 * <p>
	 * This method is used to test the register to the system as a user. If we introduce wrong datas, an expection must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 4. An actor who is not authenticated must be able to:
	 * <p>
	 * 1. Register to the system as a user
	 * <p>
	 * Case 1: Everything is correct <br>
	 * Case 2: The username is missing <br>
	 * Case 3: The password is missing
	 */
	@Test
	public void driverRegisterAsAUser() {
		final Object testingData[][] = {
			{
				"username", "password", null
			}, {
				"username2", "", IllegalArgumentException.class
			}, {
				"", "password", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing registering to the system as a user
	 * <p>
	 * 
	 * @param username
	 *            The username of the user whe are registering
	 * @param password
	 *            The password of the user
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 */
	private void templateRegister(final String username, final String password, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final User u = this.userService.create();
			u.getUserAccount().setUsername(username);
			u.getUserAccount().setPassword(password);
			u.setName("Name");
			u.setSurname("Surname");
			u.setEmail("email@acme.com");
			u.setAddress("Address");
			u.setPhone("+654321987");
			this.userService.save(u);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the lists of user who User1 is following
	 * <p>
	 * Functional Requirement:
	 * <p>
	 * 16. An actor who is authenticated as a user must be able to:
	 * <p>
	 * 3. List the users who he or she follows.
	 */
	@Test
	public void testListFollowing() {
		final User u = this.userService.findOne(this.getEntityId("User1"));
		final List<User> users = new ArrayList<>(u.getFollowing());
		Assert.notNull(users);
	}

	/**
	 * Tests the lists of followers from User1
	 * <p>
	 * Functional Requirement:
	 * <p>
	 * 16. An actor who is authenticated as a user must be able to:
	 * <p>
	 * 4. List the users who follow him or her.
	 */
	@Test
	public void testListFollowers() {
		final User u = this.userService.findOne(this.getEntityId("User1"));
		final List<User> users = new ArrayList<>(u.getFollowers());
		Assert.notNull(users);
	}

}
