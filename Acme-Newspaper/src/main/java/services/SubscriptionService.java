
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubscriptionRepository;
import domain.Newspaper;
import domain.Subscription;
import forms.SubscriptionForm;

@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository	subscriptionRepository;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private CustomerService			customerService;


	public Subscription create() {
		final Subscription s = new Subscription();
		s.setCustomer(this.customerService.findByPrincipal());
		s.setVolume(false);
		return s;
	}

	public Subscription save(final Subscription s) {
		final Newspaper n = this.newspaperService.findOne(s.getNewspaper().getId());
		Assert.isTrue(!n.isFree());
		//Assert.isTrue(n.getPublicationDate() != null);
		Assert.isNull(this.getSubscriptionByNewspaperAndPrincipal(n.getId()));
		final Subscription saved = this.subscriptionRepository.save(s);
		return saved;
	}

	public void delete(final int subscriptionId) {
		this.subscriptionRepository.delete(subscriptionId);
	}

	public Collection<Subscription> findAll() {
		return this.subscriptionRepository.findAll();
	}

	public void subscribeVolume(final SubscriptionForm s) {
		Subscription sub;
		for (final Newspaper n : s.getVolume().getNewspapers())
			if (n.isFree() == false)
				if (this.getSubscriptionByNewspaperAndPrincipal(n.getId()) == null) {
					sub = this.create();
					sub.setNewspaper(n);
					sub.setCreditCard(s.getCreditCard());
					sub.setVolume(true);
					this.save(sub);
				}
		s.getVolume().getCustomers().add(this.customerService.findByPrincipal());
	}

	public Subscription getSubscriptionByNewspaperAndPrincipal(final int newspaperId) {
		final int customerId = this.customerService.findByPrincipal().getId();
		return this.subscriptionRepository.getSubscriptionByNewspaperAndPrincipal(newspaperId, customerId);
	}
	public Collection<Subscription> getSubscriptionByNewspaper(final int newspaperId) {
		return this.subscriptionRepository.getSubscriptionByNewspaper(newspaperId);
	}

}
