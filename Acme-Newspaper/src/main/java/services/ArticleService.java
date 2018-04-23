
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Admin;
import domain.Article;
import domain.FollowUp;
import domain.TabooWord;
import domain.User;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository	articleRepository;

	// Supporting services ----------------------------------------------------	
	@Autowired
	private UserService			userService;
	@Autowired
	private TabooWordService	tabooWordService;
	@Autowired
	private FollowUpService		followUpService;
	@Autowired
	private Validator			validator;
	@Autowired
	private AdminService		adminService;


	// Constructors -----------------------------------------------------------
	public ArticleService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Article createArticle() {
		final User creator = this.userService.findByPrincipal();
		final Article res = new Article();
		res.setCreator(creator);
		res.setMarked(false);
		res.setMoment(null);
		final List<String> urls = new ArrayList<String>();
		res.setPictureUrls(urls);
		return res;
	}
	public Article save(final Article article) {
		assert article != null;

		Article res;
		final User user = this.userService.findByPrincipal();
		Assert.isTrue(article.getCreator().equals(user));
		Assert.isTrue(article.getNewspaper().getPublicationDate() == null);

		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			taboow = article.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= article.getSummary().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= article.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		article.setMarked(taboow);
		res = this.articleRepository.save(article);
		return res;
	}
	public void delete(final Article article) {
		assert article != null;
		assert article.getId() != 0;
		final List<Admin> admins = new ArrayList<Admin>(this.adminService.findAll());
		Assert.isTrue(this.adminService.findByPrincipal().equals(admins.get(0)));
		Assert.isTrue(this.articleRepository.findOne(article.getId()) != null);
		final Collection<FollowUp> followups = this.followUpService.findFollowUpsByArticle(article.getId());
		for (final FollowUp f : followups)
			this.followUpService.delete(f);
		this.articleRepository.delete(article.getId());

	}
	public Collection<Article> findAll() {
		Collection<Article> res;
		Assert.notNull(this.articleRepository);
		res = this.articleRepository.findAll();

		return res;
	}

	public Article findOne(final int articleId) {
		Assert.isTrue(articleId != 0);
		Article res;
		res = this.articleRepository.findOne(articleId);

		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<Article> findArticlesByNewspaper(final int newspaperId) {
		final Collection<Article> res = this.articleRepository.findArticlesByNewspaper(newspaperId);
		return res;
	}
	public Collection<Article> findPublishedArticlesByUser(final int userId) {
		final Collection<Article> res = this.articleRepository.findPublishedArticlesByUser(userId);
		return res;
	}
	public Article reconstruct(final Article article, final BindingResult binding) {
		Article result;
		if (article.getId() == 0) {
			result = article;
			final User creator = this.userService.findByPrincipal();
			result.setCreator(creator);
			result.setMarked(false);
			result.setMoment(null);
		} else {
			result = this.articleRepository.findOne(article.getId());
			Assert.isTrue(!result.isFinalMode());
			result.setTitle(article.getTitle());
			result.setSummary(article.getSummary());
			result.setBody(article.getBody());
			result.setFinalMode(article.isFinalMode());
			result.setPictureUrls(article.getPictureUrls());
			result.setNewspaper(article.getNewspaper());
		}
		this.validator.validate(result, binding);
		return result;
	}
	public Collection<Article> findMarkedArticlesByUser() {
		final Collection<Article> res = this.articleRepository.findMarkedArticles();
		return res;
	}
	public Collection<Article> findEditableArticlesByUser() {
		final Collection<Article> res = this.articleRepository.findEditableArticlesByUser(this.userService.findByPrincipal().getId());
		return res;
	}
	public Collection<Article> findArticlesByKeyword(final String keyword) {
		return this.articleRepository.findArticlesByKeyword(keyword);
	}
}
