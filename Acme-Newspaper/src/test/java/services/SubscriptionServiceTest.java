
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Newspaper;
import domain.Subscription;
import domain.Volume;
import forms.SubscriptionForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubscriptionServiceTest extends AbstractTest {

	@Autowired
	private SubscriptionService	subscriptionService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private VolumeService		volumeService;


	/**
	 * Tests the creation of subscriptions.
	 * <p>
	 * This method is used to test the creation of empty subscriptions before passing them to the corresponding views.
	 * 
	 * 22.1. Subscribe to a private newspaper by providing a valid credit card.
	 * 
	 */
	@Test
	public void testCreateSubscription() {
		super.authenticate("customer4");
		final Subscription res = this.subscriptionService.create();
		Assert.notNull(res);
		Assert.isTrue(this.customerService.findByPrincipal().equals(res.getCustomer()));
		super.unauthenticate();
	}

	/**
	 * Tests the saving of subscriptions.
	 * <p>
	 * This method tests the creation and later saving of subscriptions as it would be done by a customer in the corresponding views.
	 * 
	 * 22.1. Subscribe to a private newspaper by providing a valid credit card.
	 * 
	 * Case 1: Customer4 subscribes to newspaper7. Any exception is expected.
	 * 
	 * Case 2: Customer3 subscribes to newspaper7. Any exception is expected.
	 * 
	 * Case 3: Customer1 subscribes to newspaper7 (Just subscribed). IllegalArgumentException is expected.
	 * 
	 * Case 4: Customer4 subscribes to newspaper6 (Not published yet). IllegalArgumentException is expected.
	 * 
	 * Case 5: Customer4 subscribes to newspaper2 (Public newspaper). IllegalArgumentException is expected.
	 * 
	 * Case 6: Customer 4 subscribes to newspaper1 (Not Published and public). IllegalArgumentException is expected.
	 * 
	 * Case 7: Customer4 subscribes to newspaper8. Any exception is expected.
	 * 
	 * Case 8: Customer3 subscribes to newspaper8. Any exception is expected.
	 * 
	 * Case 9: Customer1 subscribes to newspaper8 (Just subscribed). IllegalArgumentException is expected.
	 * 
	 * Case 10: Customer2 subscribes to newspaper8 (Just subscribed). IllegalArgumentException is expected.
	 * 
	 */

	@Test
	public void driverSaveService() {
		final Object testingData[][] = {
			{
				"customer4", "Newspaper7", null
			}, {
				"customer3", "Newspaper7", null
			}, {
				"customer1", "Newspaper7", IllegalArgumentException.class
			}, {
				"customer4", "Newspaper6", IllegalArgumentException.class
			}, {
				"customer4", "Newspaper2", IllegalArgumentException.class
			}, {
				"customer4", "Newspaper1", IllegalArgumentException.class
			}, {
				"customer4", "Newspaper8", null
			}, {
				"customer3", "Newspaper8", null
			}, {
				"customer1", "Newspaper8", IllegalArgumentException.class
			}, {
				"customer2", "Newspaper8", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the saving of services.
	 * <p>
	 * This method defines the template used for the tests that check the saving of services.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * 
	 * @param newspaperId
	 *            The id of the newspaper that user want to subscribe.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSave(final String username, final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Subscription res = this.subscriptionService.create();
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolderName("HolderName");
			creditCard.setBrandName("Visa");
			creditCard.setNumber("4148835610496873");
			creditCard.setExpMonth(11);
			creditCard.setExpYear(12);
			creditCard.setCvv(332);
			res.setCreditCard(creditCard);
			res.setNewspaper(this.newspaperService.findOne(newspaperId));
			this.subscriptionService.save(res);
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a subscription.
	 * 
	 * <p>
	 * This method tests that a subscription can be deleted only by an admin (Security limit this restriction).
	 * 
	 * 7.2.Remove a newspaper that he or she thinks is inappropriate. Removing a newspaper implies removing all of the articles of which it is composed.
	 */

	@Test
	public void testDeleteSubscription() {
		final int newspaperId = super.getEntityId("Newspaper7");
		super.authenticate("customer4");
		final Subscription res = this.subscriptionService.create();
		final CreditCard creditCard = new CreditCard();
		creditCard.setHolderName("HolderName");
		creditCard.setBrandName("Visa");
		creditCard.setNumber("4148835610496873");
		creditCard.setExpMonth(11);
		creditCard.setExpYear(12);
		creditCard.setCvv(332);
		res.setCreditCard(creditCard);
		res.setNewspaper(this.newspaperService.findOne(newspaperId));
		final Subscription saved = this.subscriptionService.save(res);
		Assert.notNull(this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(newspaperId));
		this.subscriptionService.delete(saved.getId());
		Assert.isNull(this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(newspaperId));
		super.unauthenticate();

	}

	/**
	 * The method that I are testing is necessary for display newspapers to know if the user logged is subscribed.
	 * 
	 * Not exist explicit requirements.
	 * 
	 * Case 1: Show if customer1 has a subscription to newspaper7. Nothing is expected.
	 * 
	 * Case 2: List of customer2 subscription. Nothing is expected.
	 * 
	 * Case 3: List of customer3 subscription. Nothing is expected.
	 */
	@Test
	public void driverGetSubscriptionByNewspaperAndPrincipal() {
		final Object testingData[][] = {
			{
				"customer1", "Newspaper7", true, null
			}, {
				"customer2", "Newspaper8", true, null
			}, {
				"customer3", "Newspaper7", false, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSubscriptionByNewspaperAndPrincipal((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * The method I are testing is necessary for display newspapers to know if the user logged is subscribed.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param newspaperId
	 *            The id of the newspaper that we want to know if the customer is subscribed.
	 * @param subscribed
	 *            If customer is subscribed.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSubscriptionByNewspaperAndPrincipal(final String username, final int newspaperId, final boolean subscribed, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Subscription s = this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(newspaperId);
			if (s != null)
				Assert.isTrue(subscribed);
			else
				Assert.isTrue(!subscribed);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * This method is necessary for delete newspapers.
	 * 
	 * Not exist explicit requirements.
	 * 
	 * Case 1: List of subscription by newspaper 1. Nothing is expected.
	 * 
	 * Case 2: List of subscription by newspaper 7. Nothing is expected.
	 * 
	 * Case 3: List of subscription by newspaper 8. Nothing is expected.
	 */

	@Test
	public void drivergetSubscriptionByNewspaper() {
		final Object testingData[][] = {
			{
				"Newspaper1", 0, null
			}, {
				"Newspaper7", 1, null
			}, {
				"Newspaper8", 1, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSubscriptionByNewspaper(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * This method is necessary for delete newspapers.
	 * 
	 * @param newspaperId
	 *            The id of the newspaper that we want to know its subscriptions.
	 * @param minimumLength
	 *            The minimum expected length of the subscriptions that the newspaper has.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSubscriptionByNewspaper(final int newspaperId, final int minimumLength, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			final Collection<Subscription> res = this.subscriptionService.getSubscriptionByNewspaper(newspaperId);
			Assert.notNull(res);
			Assert.isTrue(res.size() >= minimumLength);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the method findAll
	 * <p>
	 * This method is used to test the list of all subscriptions
	 * 
	 * Not exist explicit requirements.
	 */
	@Test
	public void testFindAll() {
		final Collection<Subscription> subsc = this.subscriptionService.findAll();
		Assert.notNull(subsc);
		Assert.isTrue(subsc.size() >= 3);
	}

	/**
	 * This method test the subscription to a volume by a customer
	 * 
	 * 9.1. Subscribe to a volume by providing a credit card.
	 * 
	 * Case 1: Customer 1 subscribes to a volume 3. Nothing is expected.
	 * 
	 * Case 2: Customer 1 subscribes to a volume 1 (Free). Nothing is expected.
	 * 
	 * Case 3: Null subscribes to a volume. IllegalArgumentException is expected.
	 */

	@Test
	public void driverSubscribeVolume() {
		final Object testingData[][] = {
			{
				"customer1", "Volume3", null
			}, {
				"customer1", "Volume1", null
			}, {
				null, "Volume3", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSubscribeVolume((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * This template is used to test the subscription of a volume
	 * 
	 * @param username
	 *            The username of the customer.
	 * @param volumeId
	 *            The id of the volume that we want to subscribe it.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSubscribeVolume(final String username, final int volumeId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final SubscriptionForm sf = new SubscriptionForm();
			final CreditCard cd = new CreditCard();
			cd.setHolderName("Manuel");
			cd.setBrandName("Visa");
			cd.setNumber("4935776824728742");
			cd.setExpMonth(11);
			cd.setExpYear(22);
			cd.setCvv(356);
			sf.setCreditCard(cd);
			final Volume v = this.volumeService.findOne(volumeId);
			sf.setVolume(v);
			this.subscriptionService.subscribeVolume(sf);
			Assert.isTrue(this.volumeService.findOne(volumeId).getCustomers().contains(this.customerService.findByPrincipal()));
			for (final Newspaper n : this.volumeService.findOne(volumeId).getNewspapers())
				if (!n.isFree())
					Assert.notNull(this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(n.getId()));
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
