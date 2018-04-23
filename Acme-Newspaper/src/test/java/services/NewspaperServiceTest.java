
package services;

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
	 * Template for testing the saving of services.
	 * <p>
	 * This method defines the template used for the tests that check the saving of services.
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
	 * Template for testing the editing of services.
	 * <p>
	 * This method defines the template used for the tests that check the editing of services.
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
	 * Template for testing the publication of services.
	 * <p>
	 * This method defines the template used for the tests that check the publishing of services.
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
		this.newspaperService.delete(newspaperId);
		Assert.isNull(this.newspaperService.findOne(newspaperId));
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

}
