
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NewspaperServiceTest extends AbstractTest {

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private UserService			userService;


	/**
	 * Tests the creation of newspapers.
	 * <p>
	 * This method is used to test the creation of empty newspapers before passing them to the corresponding views.
	 * 
	 * 6.1.Create a newspaper. A user who has created a newspaper is commonly referred to a publisher.
	 * 
	 */
	@Test
	public void testCreateNewspaper() {
		super.authenticate("user1");
		final Newspaper n = this.newspaperService.create();
		Assert.notNull(n);
		Assert.isTrue(this.userService.findByPrincipal().equals(n.getCreator()));
		super.unauthenticate();
	}

	/**
	 * Tests the saving of newspapers.
	 * <p>
	 * This method tests the creation and later saving of newspapers as it would be done by a user in the corresponding views.
	 * 
	 * 6.1.Create a newspaper. A user who has created a newspaper is commonly referred to a publisher.
	 * 
	 * Case 1: User1 create a newspaper. Any exception is expected.
	 * 
	 * Case 2: User1 create a newspaper with price (private). Any exception is expected.
	 * 
	 * Case 3: User1 create a newspaper published. IllegalArgumentException is expected.
	 * 
	 * Case 4: User1 create a newspaper published with taboo words. IllegalArgumentException is expected.
	 * 
	 * Case 5: User1 create a newspaper with taboo words. Any exception is expected.
	 */

	@Test
	public void driverSaveNewspaper() {
		final Object testingData[][] = {
			{
				"user1", 0.0, false, false, null
			}, {
				"user1", 5.0, false, false, null
			}, {
				"user1", 5.0, true, false, IllegalArgumentException.class
			}, {
				"user1", 5.0, true, true, IllegalArgumentException.class
			}, {
				"user1", 5.0, false, true, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (double) testingData[i][1], (boolean) testingData[i][2], (boolean) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	/**
	 * Template for testing the saving of newspapers.
	 * <p>
	 * This method defines the template used for the tests that check the saving of newspapers.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param price
	 *            The price of the newspaper that user want to create.
	 * @param published
	 *            If newspaper is published
	 * @param tabooword
	 *            If newspaper contains taboo words
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSave(final String username, final double price, final boolean published, final boolean tabooWords, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Newspaper n = this.newspaperService.create();
			n.setTitle("Prueba de periódico");
			n.setDescription("Esto es una prueba de creación de un periódico");
			n.setPrice(price);
			if (published)
				n.setPublicationDate(new Date(System.currentTimeMillis() - 10000));
			if (tabooWords)
				n.setDescription("Sex words");
			final Newspaper saved = this.newspaperService.save(n);
			Assert.isTrue(!saved.isFree() == saved.getPrice() > 0);
			Assert.isTrue(tabooWords == saved.isMarked());
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the editing of newspapers.
	 * <p>
	 * This method tests the edition of newspapers as it would be done by a user in the corresponding views.
	 * 
	 * 6.1.Create a newspaper. A user who has created a newspaper is commonly referred to a publisher.
	 * 
	 * Case 1: User1 create a newspaper. Any exception is expected.
	 * 
	 * Case 2: User1 create a newspaper with price (private). Any exception is expected.
	 * 
	 * Case 3: User1 create a newspaper published. IllegalArgumentException is expected.
	 * 
	 * Case 4: User1 create a newspaper published with taboo words. IllegalArgumentException is expected.
	 * 
	 * Case 5: User1 create a newspaper with taboo words. Any exception is expected.
	 */

	@Test
	public void driverEditNewspaper() {
		final Object testingData[][] = {
			{
				"user1", "Newspaper1", null
			}, {
				"user2", "Newspaper1", IllegalArgumentException.class
			}, {
				"user1", "Newspaper2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the editing of newspapers.
	 * <p>
	 * This method defines the template used for the tests that check the editing of newspapers.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param newspaperId
	 *            The id of newspaper that we want to edit.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateEdit(final String username1, final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username1);
			final Newspaper n = this.newspaperService.findOne(newspaperId);
			n.setTitle("Cambiando el título");
			this.newspaperService.save(n);
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the publishing of newspapers.
	 * <p>
	 * This method tests the publication of newspapers as it would be done by a user in the corresponding views.
	 * 
	 * 6.2. Publish a newspaper that he or she is created. Note that no newspaper can be published until each of the articles of which it is composed is saved in final mode.
	 * 
	 * Case 1: User1 publish a newspaper. Any exception is expected.
	 * 
	 * Case 2: User2 publish a newspaper not created by him. Any exception is expected.
	 * 
	 * Case 3: User1 publish a newspaper but all of his articles not are in final mode. IllegalArgumentException is expected.
	 * 
	 */

	@Test
	public void driverPublishNewspaper() {
		final Object testingData[][] = {
			{
				"user1", "Newspaper6", null
			}, {
				"user2", "Newspaper1", IllegalArgumentException.class
			}, {
				"user1", "Newspaper1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePublish((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the publication of newspapers.
	 * <p>
	 * This method defines the template used for the tests that check the publishing of newspapers.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param newspaperId
	 *            The id of newspaper that we want to edit.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templatePublish(final String username1, final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username1);
			this.newspaperService.publish(newspaperId);
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a newspaper.
	 * 
	 * <p>
	 * This method tests that a newspaper can be deleted only by an admin (Security limit this restriction).
	 * 
	 * 7.2.Remove a newspaper that he or she thinks is inappropriate. Removing a newspaper implies removing all of the articles of which it is composed.
	 * 
	 */

	@Test
	public void testDeleteNewspaper() {
		super.authenticate("admin");
		final int newspaperId = super.getEntityId("Newspaper7");
		final Newspaper n = this.newspaperService.findOne(newspaperId);
		this.newspaperService.delete(newspaperId);
		Assert.isTrue(!this.newspaperService.findAll().contains(n));
		super.authenticate(null);

	}

	/**
	 * Tests the listing of newspapers marked.
	 * <p>
	 * This method tests the listing of the newspapers that contains taboo words.
	 * 
	 * 17.3. List the newspapers that contain taboo words.
	 */
	@Test
	public void testFindMarkedNewspapers() {
		Assert.isTrue(this.newspaperService.findMarkedNewspaper().size() >= 1);
	}

	/**
	 * Tests the listing of newspapers publicated.
	 * <p>
	 * This method tests the listing of the newspapers publicated.
	 * 
	 * 4.2. List the newspapers that are published and browse their articles.
	 */
	@Test
	public void testFindPublicatedNewspapers() {
		Assert.isTrue(this.newspaperService.findPublicatedNewspaper().size() >= 7);
	}

	/**
	 * Tests the listing of newspapers publicated.
	 * <p>
	 * This method tests the listing of the newspapers publicated.
	 * 
	 * 6.3. Write an article and attach it to any newspaper that has not been published, yet.
	 * 
	 */
	@Test
	public void testFindNotPublicatedNewspapers() {
		Assert.isTrue(this.newspaperService.findNotPublicatedNewspaper().size() >= 2);
	}

	/**
	 * Tests the listing of all newspapers.
	 * <p>
	 * This method tests the listing of the newspapers.
	 * 
	 * 7.2. Remove a newspaper that he or she thinks is inappropriate.
	 * 
	 */
	@Test
	public void testFindAllNewspapers() {
		Assert.isTrue(this.newspaperService.findAll().size() >= 9);
	}

	/**
	 * Tests the findOne method.
	 * <p>
	 * This method tests that we can find a newspaper
	 * 
	 * Not exist explicit requirements.
	 * 
	 */
	@Test
	public void testFindOne() {
		super.authenticate("user1");
		final Newspaper n = this.newspaperService.create();
		n.setTitle("Prueba de periódico");
		n.setDescription("Esto es una prueba de creación de un periódico");
		final Newspaper saved = this.newspaperService.save(n);
		super.authenticate(null);
		Assert.isTrue(this.newspaperService.findOne(saved.getId()).equals(saved));
	}

	/**
	 * Tests the listing of newspapers created by principal.
	 * <p>
	 * This method tests the listing of the newspapers created by principal.
	 * 
	 * Not exist explicit requirements.
	 * 
	 * Case 1: List newspapers created by user 1. No exception is expected.
	 * 
	 * Case 2: List newspapers created by user 2. No exception is expected.
	 * 
	 * Case 3: List newspapers created by nothing. IllegalArgumentException is expected.
	 */

	@Test
	public void driverNewspaperCreatedByPrincipal() {
		final Object testingData[][] = {
			{
				"user1", 7, null
			}, {
				"user2", 1, null
			}, {
				null, 0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperCreatedByPrincipal((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers created by principal
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param minimumLength
	 *            The minimum expected length of the list of rendezvouses that the user has created.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperCreatedByPrincipal(final String username, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Collection<Newspaper> res = this.newspaperService.findNewspaperCreatedByPrincipal();
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of newspapers created an user.
	 * <p>
	 * This method tests the listing of the newspapers created by an user.
	 * 
	 * Not exist explicit requirements.
	 * 
	 * Case 1: List newspapers created by user 1. No exception is expected.
	 * 
	 * Case 2: List newspapers created by user 2. No exception is expected.
	 * 
	 */
	@Test
	public void driverNewspaperCreatedByUserId() {
		final Object testingData[][] = {
			{
				"User1", 7, null
			}, {
				"User2", 1, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperCreatedByUserId(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers created by an user.
	 * 
	 * @param username
	 *            The username of the user that we want to know his newspapers.
	 * @param minimumLength
	 *            The minimum expected length of the list of rendezvouses that the user has created.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperCreatedByUserId(final int userId, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			Assert.notNull(this.userService.findOne(userId));
			final Collection<Newspaper> res = this.newspaperService.findNewspaperCreatedByUserId(userId);
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of newspapers searched.
	 * <p>
	 * This method tests the listing of the newspapers searched by a keyword
	 * 
	 * 4.5. Search for a published newspaper using a single keyword that must appear somewhere in its title or its description.
	 * 
	 * Case 1: List newspapers that contains "newspaper1"
	 * 
	 * Case 2: List newspapers that contains "newspaper"
	 * 
	 * Case 3: List newspapers that contains "this"
	 * 
	 */
	@Test
	public void driverNewspaperByKeyword() {
		final Object testingData[][] = {
			{
				"newspaper1", 0, null
			}, {
				"newspaper", 7, null
			}, {
				"this", 6, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperByKeyword((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers that contains a keyword
	 * 
	 * @param keyword
	 *            The keyword to search newspapers.
	 * @param minimumLength
	 *            The minimum expected length of the list of rendezvouses that the user has created.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperByKeyword(final String keyword, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Collection<Newspaper> res = this.newspaperService.findNewspapersByKeyword(keyword);
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of newspapers in which an agent has placed an advertisement
	 * <p>
	 * This method tests the listing of the newspapers in which an agent has placed an advertisement
	 * 
	 * 4.3 List the newspapers in which they have placed an advertisement.
	 * 
	 * Case 1: List newspapers by agent 1. No exception is expected.
	 * 
	 * Case 2: List newspapers by agent 2. No exception is expected.
	 * 
	 * Case 3: List newspapers by nothing. IllegalArgumentException is expected.
	 */

	@Test
	public void driverNewspaperWithAdvertisementByPrincipal() {
		final Object testingData[][] = {
			{
				"agent1", 6, null
			}, {
				"agent2", 1, null
			}, {
				null, 0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperWithAdvertisementByPrincipal((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers with advertisements by principal
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param minimumLength
	 *            The minimum expected length of the list of newspapers with advertisements.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperWithAdvertisementByPrincipal(final String username, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Collection<Newspaper> res = this.newspaperService.findNewspapersWithAdvertisementByAgent();
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of newspapers in which an agent has not placed an advertisement
	 * <p>
	 * This method tests the listing of the newspapers in which an agent has not placed an advertisement
	 * 
	 * 4.4 List the newspapers in which they have not placed any advertisements.
	 * 
	 * Case 1: List newspapers by agent 1. No exception is expected.
	 * 
	 * Case 2: List newspapers by agent 2. No exception is expected.
	 * 
	 * Case 3: List newspapers by nothing. IllegalArgumentException is expected.
	 */

	@Test
	public void driverNewspaperWithoutAdvertisementByPrincipal() {
		final Object testingData[][] = {
			{
				"agent1", 1, null
			}, {
				"agent2", 6, null
			}, {
				null, 0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperWithoutAdvertisementByPrincipal((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers without advertisements by principal
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param minimumLength
	 *            The minimum expected length of the list of newspapers without any advertisements.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperWithoutAdvertisementByPrincipal(final String username, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Collection<Newspaper> res = this.newspaperService.findNewspapersWithoutAdvertisementByAgent();
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of newspapers in which a user can add to a volume.
	 * <p>
	 * This method tests the listing of the newspapers in which a user can add to a volume
	 * 
	 * Not exist explicit requeriments
	 * 
	 * Case 1: List newspapers by user 1 and volume 1. No exception is expected.
	 * 
	 * Case 2: List newspapers by user 1 and volume 2. No exception is expected.
	 * 
	 * Case 3: List newspapers by user 3 and volume 3. No exception is expected.
	 * 
	 * Case 4: List newspapers by nothing. IllegalArgumentException is expected.
	 */

	@Test
	public void driverNewspaperPublicatedByPrincipal() {
		final Object testingData[][] = {
			{
				"user1", "Volume1", 1, null
			}, {
				"user1", "Volume2", 4, null
			}, {
				"user3", "Volume3", 0, null
			}, {
				null, "Volume5", 0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNewspaperPublicatedByPrincipal((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing the listing of newspapers.
	 * <p>
	 * This method defines the template used to test the listing of newspapers without advertisements by principal
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param minimumLength
	 *            The minimum expected length of the list of newspapers without any advertisements.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateFindNewspaperPublicatedByPrincipal(final String username, final int volumeId, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Collection<Newspaper> res = this.newspaperService.findPublicatedNewspaperByPrincipal(volumeId);
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
