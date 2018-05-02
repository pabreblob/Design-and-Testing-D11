
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
import domain.TabooWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TabooWordServiceTest extends AbstractTest {

	@Autowired
	private TabooWordService	tabooWordService;


	/**
	 * Tests the creation and saving of Taboo Words in the system.
	 * <p>
	 * Functional Requirement 17.1: An actor who is authenticated as an administrator must be able to: Manage a list of taboo words.
	 * <p>
	 * Case 1: An administrator creates a Taboo Word. No exception is expected.<br>
	 * Case 2: An unidentified Actor tries to create a Taboo Word. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as an User tries to create a Taboo Word. An <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverCreateAndSaveTabooWord() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the creation and saving of Taboo Words.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new Taboo Words in the system.
	 * 
	 * @param actor
	 *            The actor that will try to create the Taboo Word.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateAndSave(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final TabooWord tabooword = this.tabooWordService.create();
			tabooword.setWord("test");
			final TabooWord res = this.tabooWordService.save(tabooword);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.tabooWordService.findOne(res.getId())));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of Taboo Words by an Administrator of the System
	 * <p>
	 * Functional Requirement 17.1: An actor who is authenticated as an administrator must be able to: Manage a list of taboo words.
	 * <p>
	 * Case 1: An Administrator deletes a Taboo Word. No execption is expected.<br>
	 * Case 2: An unidentified Actor tries to delete a Taboo Word. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as a User tries to delete a Taboo Word. An <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverDeleteChirp() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion and saving of Chirps.
	 * <p>
	 * This method defines the template used for the tests that check the deletion and saving of new Chirps in the system.
	 * 
	 * @param actor
	 *            The actor that will try to delete the Chirp.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final List<TabooWord> taboowords = new ArrayList<TabooWord>(this.tabooWordService.findAll());
			final TabooWord res = taboowords.get(0);
			this.tabooWordService.delete(res);
			Assert.isTrue(!this.tabooWordService.findAll().contains(res));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
