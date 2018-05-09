
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chirp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChirpServiceTest extends AbstractTest {

	@Autowired
	private ChirpService	chirpService;


	/**
	 * Tests the creation and saving of Chirps in the system.
	 * <p>
	 * Functional Requirement 16.1: An actor who is authenticated as a user must be able to: Post a chirp. [...]
	 * <p>
	 * Case 1: An User creates a Chirp. No exception is expected.<br>
	 * Case 2: An unidentified Actor tries to create a Chirp. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as a Customer tries to create a Chirp. An <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverCreateAndSaveChirp() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"customer1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the creation and saving of Chirps.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new Chirps in the system.
	 * 
	 * @param actor
	 *            The actor that will try to create the Chirp.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateAndSave(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final Chirp chirp = this.chirpService.create();
			chirp.setTitle("test");
			chirp.setDescription("testdesc");
			final Chirp recons = this.chirpService.reconstruct(chirp, null);
			final Chirp res = this.chirpService.save(recons);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.chirpService.findOne(res.getId())));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of Chirps by an Administrator of the System
	 * <p>
	 * Functional requirement 17.5: An actor who is authenticated as an administrator must be able to: Remove a chirp that he or she thinks is inappropriate.
	 * <p>
	 * Case 1: An Administrator deletes a Chirp. No execption is expected.<br>
	 * Case 2: An unidentified Actor tries to delete a Chirp. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as a User tries to delete a Chirp. An <code>IllegalArgumentException</code> is expected.
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
			final List<Chirp> chirps = new ArrayList<Chirp>(this.chirpService.findAll());
			final Chirp res = chirps.get(0);
			this.chirpService.delete(res);
			Assert.isTrue(!this.chirpService.findAll().contains(res));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the finding of all marked chirps in the system.
	 * <p>
	 * Functional Requirement 17.4: An actor who is authenticated as an administrator must be able to: List the chirps that contain taboo words.
	 */
	@Test
	public void testFindMarkedChirps() {
		super.authenticate("user1");
		Chirp chirp = this.chirpService.create();
		chirp.setTitle("sex");
		chirp.setDescription("desc1");
		chirp = this.chirpService.reconstruct(chirp, null);
		chirp = this.chirpService.save(chirp);
		super.unauthenticate();
		final Collection<Chirp> marked = this.chirpService.findMarked();
		Assert.isTrue(marked.contains(chirp));
	}
}
