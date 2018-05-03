
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TabooWordRepository;
import domain.Advertisement;
import domain.Article;
import domain.Chirp;
import domain.FollowUp;
import domain.Newspaper;
import domain.TabooWord;

@Service
@Transactional
public class TabooWordService {

	@Autowired
	private TabooWordRepository		tabooWordRepository;
	@Autowired
	private AdminService			adminService;
	@Autowired
	private ArticleService			articleService;
	@Autowired
	private FollowUpService			followupService;
	@Autowired
	private ChirpService			chirpService;
	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private NewspaperService		newspaperService;


	public TabooWordService() {
		super();
	}

	public TabooWord create() {
		Assert.notNull(this.adminService.findByPrincipal());
		final TabooWord res = new TabooWord();

		return res;
	}

	public TabooWord findOne(final int tabooWordId) {
		Assert.isTrue(tabooWordId > 0);
		Assert.notNull(this.tabooWordRepository.findOne(tabooWordId));
		return this.tabooWordRepository.findOne(tabooWordId);
	}

	public Collection<TabooWord> findAll() {
		return this.tabooWordRepository.findAll();
	}

	public TabooWord save(final TabooWord tw) {
		Assert.notNull(tw);
		Assert.notNull(this.adminService.findByPrincipal());

		final TabooWord res = this.tabooWordRepository.save(tw);

		this.updateNewWord(tw);

		return res;
	}

	public void delete(final TabooWord tw) {
		Assert.notNull(tw);
		Assert.isTrue(tw.getId() > 0);
		Assert.notNull(this.adminService.findByPrincipal());

		this.tabooWordRepository.delete(tw);

		this.updateWords();
	}

	//----------------Auxiliar-----------------

	protected void updateNewWord(final TabooWord tw) {
		final Collection<Article> arts = this.articleService.findAll();
		final Collection<Chirp> chirps = this.chirpService.findAll();
		final Collection<FollowUp> folls = this.followupService.findAll();
		final Collection<Newspaper> newsps = this.newspaperService.findAll();
		final Collection<Advertisement> advs = this.advertisementService.findAll();

		for (final Article a : arts)
			a.setMarked(a.isMarked() || a.getBody().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || a.getSummary().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || a.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));

		for (final Chirp c : chirps)
			c.setMarked(c.isMarked() || c.getDescription().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || c.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));

		for (final FollowUp f : folls)
			f.setMarked(f.isMarked() || f.getBody().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || f.getSummary().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || f.getTitle().contains(tw.getWord()));

		for (final Newspaper n : newsps)
			n.setMarked(n.isMarked() || n.getDescription().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || n.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));

		for (final Advertisement a : advs)
			a.setMarked(a.isMarked() || a.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));
	}
	protected void updateWords() {
		final Collection<TabooWord> all = this.findAll();

		final Collection<Article> arts = this.articleService.findAll();
		final Collection<Chirp> chirps = this.chirpService.findAll();
		final Collection<FollowUp> folls = this.followupService.findAll();
		final Collection<Newspaper> newsps = this.newspaperService.findAll();
		final Collection<Advertisement> advs = this.advertisementService.findAll();

		for (final Article a : arts) {
			a.setMarked(false);
			for (final TabooWord tw : all)
				a.setMarked(a.isMarked() || a.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || a.getSummary().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*")
					|| a.getBody().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));
		}

		for (final Chirp c : chirps) {
			c.setMarked(false);
			for (final TabooWord tw : all)
				c.setMarked(c.isMarked() || c.getDescription().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || c.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));
		}

		for (final FollowUp f : folls) {
			f.setMarked(false);
			for (final TabooWord tw : all)
				f.setMarked(f.isMarked() || f.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || f.getSummary().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*")
					|| f.getBody().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));
		}

		for (final Newspaper n : newsps) {
			n.setMarked(false);
			for (final TabooWord tw : all)
				n.setMarked(n.isMarked() || n.getDescription().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*") || n.getTitle().matches(".*\\b" + tw.getWord() + "\\b.*"));
		}

		for (final Advertisement a : advs) {
			a.setMarked(false);
			for (final TabooWord tw : all)
				a.setMarked(a.isMarked() || a.getTitle().toLowerCase().matches(".*\\b" + tw.getWord() + "\\b.*"));
		}
	}
}
