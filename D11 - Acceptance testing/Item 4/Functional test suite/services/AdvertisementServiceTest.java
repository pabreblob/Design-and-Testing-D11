
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
import domain.Advertisement;
import domain.CreditCard;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private NewspaperService		newspaperService;


	/**
	 * Tests the creation and saving of advertisements in the system.
	 * <p>
	 * Functional Requirement 4.2: An actor who is authenticated as an agent must be able to: Register an advertisement and place it in a newspaper.
	 * <p>
	 * Case 1: An Agent creates a advertisement. No exception is expected.<br>
	 * Case 2: An unidentified Actor tries to create a advertisement. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as a Customer tries to create a advertisement. An <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverCreateAndSaveAdvertisement() {
		final Object testingData[][] = {
			{
				"Agent1", "Newspaper9", null
			}, {
				"", "Newspaper3", IllegalArgumentException.class
			}, {
				"Customer1", "Newspaper4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateCreateAndSave(final String actor, final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final Newspaper newspaper = this.newspaperService.findOne(newspaperId);
			final Advertisement advertisement = this.advertisementService.create(newspaper);
			advertisement.setTitle("test");
			advertisement.setBannerUrl("http://www.test.com");
			advertisement.setPageUrl("http://www.test.com");

			final CreditCard cc = new CreditCard();
			cc.setBrandName("test");
			cc.setCvv(123);
			cc.setExpMonth(11);
			cc.setExpYear(20);
			cc.setHolderName("test");
			cc.setNumber("1111222233334444");
			advertisement.setCreditCard(cc);

			final Advertisement recons = this.advertisementService.reconstruct(advertisement, null);
			final Advertisement res = this.advertisementService.save(recons);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.advertisementService.findOne(res.getId())));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of Advertisements by an Administrator of the System
	 * <p>
	 * Functional requirement 5.2: An actor who is authenticated as an administrator must be able to: Remove an advertisement that he or she thinks is inappropriate.
	 * <p>
	 * Case 1: An Administrator deletes an Advertisement. No exception is expected.<br>
	 * Case 2: An unidentified Actor tries to delete an Advertisement. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An actor identified as a User tries to delete an Advertisement. An <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverDeleteAdvertisements() {
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
	 * Template for testing the deletion of Advertisements.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of new Advertisements in the system.
	 * 
	 * @param actor
	 *            The actor that will try to delete the Advertisement.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final List<Advertisement> advertisements = new ArrayList<Advertisement>(this.advertisementService.findAll());
			final Advertisement res = advertisements.get(0);
			this.advertisementService.delete(res);
			Assert.isTrue(!this.advertisementService.findAll().contains(res));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the finding of all marked advertisements in the system.
	 * <p>
	 * Functional Requirement 5.1: An actor who is authenticated as an administrator must be able to: List the advertisements that contain taboo words in its title.
	 */
	@Test
	public void testFindMarkedAdvertisements() {
		super.authenticate("agent1");
		Advertisement advertisement = this.advertisementService.create(this.newspaperService.findOne(super.getEntityId("Newspaper9")));
		advertisement.setTitle("sex");
		advertisement.setBannerUrl("http://www.test.com");
		advertisement.setPageUrl("http://www.test.com");

		final CreditCard cc = new CreditCard();
		cc.setBrandName("test");
		cc.setCvv(123);
		cc.setExpMonth(11);
		cc.setExpYear(20);
		cc.setHolderName("test");
		cc.setNumber("1111222233334444");
		advertisement.setCreditCard(cc);

		advertisement = this.advertisementService.reconstruct(advertisement, null);
		advertisement = this.advertisementService.save(advertisement);
		super.unauthenticate();
		final Collection<Advertisement> marked = this.advertisementService.findMarked();
		Assert.isTrue(marked.contains(advertisement));
	}

}
