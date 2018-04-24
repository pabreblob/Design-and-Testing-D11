
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.Newspaper;
import domain.TabooWord;

@Service
@Transactional
public class AdvertisementService {

	@Autowired
	private AdvertisementRepository	advertisementRepository;
	@Autowired
	private AgentService			agentService;
	@Autowired
	private AdminService			adminService;
	@Autowired
	private TabooWordService		tabooWordService;
	@Autowired
	private Validator				validator;


	public AdvertisementService() {
		super();
	}

	public Advertisement create(final Newspaper n) {
		final Agent owner = this.agentService.findByPrincipal();
		Assert.notNull(owner);
		final Advertisement res = new Advertisement();
		res.setNewspaper(n);

		return res;
	}

	public Advertisement save(final Advertisement adv) {
		Assert.notNull(adv);
		Assert.isTrue(adv.getId() == 0);
		final Agent owner = this.agentService.findByPrincipal();
		Assert.isTrue(adv.getOwner().equals(owner));

		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			taboow = adv.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		adv.setMarked(taboow);

		final Advertisement res = this.advertisementRepository.save(adv);

		return res;
	}

	public Advertisement findOne(final int advId) {
		Assert.isTrue(advId > 0);
		return this.advertisementRepository.findOne(advId);
	}

	public Collection<Advertisement> findAll() {
		return this.advertisementRepository.findAll();
	}

	public void delete(final Advertisement adv) {
		Assert.notNull(this.adminService.findByPrincipal());
		Assert.notNull(adv);

		this.advertisementRepository.delete(adv);
	}

	public Collection<Advertisement> findAdvertisementByAgentId(final int agentId) {
		return this.advertisementRepository.findAdvertisementByAgentId(agentId);
	}

	public Collection<Advertisement> findAdvertisementByNewspaperId(final int newspaperId) {
		return this.advertisementRepository.findAdvertisementByNewspaperId(newspaperId);
	}

	public Collection<Advertisement> findMarked() {
		return this.advertisementRepository.findMarked();
	}

	public Advertisement reconstruct(final Advertisement adv, final BindingResult binding) {
		final Advertisement res = adv;
		final Agent owner = this.agentService.findByPrincipal();
		res.setOwner(owner);
		res.setMarked(false);

		this.validator.validate(res, binding);
		return res;
	}

}
