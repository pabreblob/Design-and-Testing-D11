
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import security.LoginService;
import domain.Advertisement;
import domain.Agent;
import domain.Article;
import domain.Newspaper;
import domain.Subscription;
import domain.TabooWord;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class NewspaperService {

	@Autowired
	private NewspaperRepository		newspaperRepository;

	@Autowired
	private UserService				userService;

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private SubscriptionService		subscriptionService;

	@Autowired
	private TabooWordService		tabooWordService;

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private AgentService			agentService;

	@Autowired
	private VolumeService			volumeService;


	public Newspaper create() {
		final Newspaper res = new Newspaper();
		res.setCreator(this.userService.findByPrincipal());
		res.setVolumes(new ArrayList<Volume>());
		return res;
	}
	public Collection<Newspaper> findAll() {
		return this.newspaperRepository.findAll();
	}

	public Newspaper findOne(final int newspaperId) {
		final Newspaper res = this.newspaperRepository.findOne(newspaperId);
		Assert.notNull(res);
		return res;
	}

	public Newspaper save(final Newspaper newspaper) {
		if (newspaper.getId() != 0)
			Assert.isTrue(this.findOne(newspaper.getId()).getCreator().equals(this.userService.findByPrincipal()));
		Assert.isNull(newspaper.getPublicationDate());
		if (newspaper.getPrice() == 0)
			newspaper.setFree(true);
		else
			newspaper.setFree(false);
		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			taboow = newspaper.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= newspaper.getDescription().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		newspaper.setMarked(taboow);
		final Newspaper saved = this.newspaperRepository.save(newspaper);
		return saved;
	}

	public void publish(final int newspaperId) {
		assert newspaperId != 0;
		final Date publicationDate = new Date(System.currentTimeMillis() - 10000);
		final Newspaper newspaper = this.findOne(newspaperId);
		Assert.notNull(newspaper);
		Assert.isTrue(this.userService.findByPrincipal().equals(newspaper.getCreator()));
		Assert.isNull(newspaper.getPublicationDate());
		for (final Article a : this.articleService.findArticlesByNewspaper(newspaperId)) {
			Assert.isTrue(a.isFinalMode());
			a.setMoment(publicationDate);
		}

		newspaper.setPublicationDate(publicationDate);
	}
	public void delete(final int newspaperId) {
		assert newspaperId != 0;
		final Newspaper newspaper = this.findOne(newspaperId);
		Assert.notNull(newspaper);
		for (final Article a : this.articleService.findArticlesByNewspaper(newspaperId))
			this.articleService.delete(a);
		for (final Subscription s : this.subscriptionService.getSubscriptionByNewspaper(newspaperId))
			this.subscriptionService.delete(s.getId());

		for (final Volume v : newspaper.getVolumes())
			v.getNewspapers().remove(newspaper);
		for (final Advertisement a : this.advertisementService.findAdvertisementByNewspaperId(newspaperId))
			this.advertisementService.delete(a);
		this.newspaperRepository.delete(newspaperId);
	}

	public Collection<Newspaper> findMarkedNewspaper() {
		return this.newspaperRepository.findMarkedNewspaper();
	}

	public Collection<Newspaper> findPublicatedNewspaper() {
		return this.newspaperRepository.findPublicatedNewspaper();
	}

	public Collection<Newspaper> findNotPublicatedNewspaper() {
		return this.newspaperRepository.findNotPublicatedNewspaper();
	}

	public Collection<Newspaper> findNewspaperCreatedByPrincipal() {
		return this.newspaperRepository.findNewspaperCreatedByUserAccountId(LoginService.getPrincipal().getId());
	}

	public Collection<Newspaper> findNewspaperCreatedByUserId(final int userId) {
		return this.newspaperRepository.findNewspaperCreatedByUserAccountId(this.userService.findOne(userId).getUserAccount().getId());
	}

	public Collection<Newspaper> findNewspapersByKeyword(final String keyword) {
		return this.newspaperRepository.findNewspapersByKeyword(keyword);
	}

	//public Collection<Newspaper> findNewspapersByvolumeId(final int volumeId) {
	//	return this.newspaperRepository.findNewspapersByVolumeId(volumeId);
	//}

	//------------------------------Acme Newspaper 2.0-------------------------
	public Collection<Newspaper> findNewspapersWithAdvertisementByAgent() {
		final Agent a = this.agentService.findByPrincipal();
		Assert.notNull(a);
		return this.newspaperRepository.findNewspapersWithAdvertisementByAgentId(a.getId());
	}

	public Collection<Newspaper> findNewspapersWithoutAdvertisementByAgent() {
		final Agent a = this.agentService.findByPrincipal();
		Assert.notNull(a);
		final Collection<Newspaper> withAdverts = this.findNewspapersWithAdvertisementByAgent();
		final Collection<Newspaper> allPublish = this.findPublicatedNewspaper();
		allPublish.removeAll(withAdverts);
		return allPublish;
	}
	public Collection<Newspaper> findPublicatedNewspaperByPrincipal(final int volumeId) {
		final User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		final Collection<Newspaper> added = this.volumeService.findOne(volumeId).getNewspapers();
		final Collection<Newspaper> res = this.newspaperRepository.findPublicatedNewspaperByUserId(u.getId());
		res.removeAll(added);
		return res;
	}
}
