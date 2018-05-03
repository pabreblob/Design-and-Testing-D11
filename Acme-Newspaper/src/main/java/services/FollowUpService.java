
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowUpRepository;
import domain.Admin;
import domain.Article;
import domain.FollowUp;
import domain.TabooWord;
import domain.User;

@Service
@Transactional
public class FollowUpService {

	@Autowired
	private FollowUpRepository	followUpRepository;

	// Supporting services ----------------------------------------------------	
	@Autowired
	private UserService			userService;
	@Autowired
	private TabooWordService	tabooWordService;
	@Autowired
	private AdminService		adminService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------
	public FollowUpService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public FollowUp createFollowUp(final Article a) {
		FollowUp res;
		final User user = this.userService.findByPrincipal();
		Assert.isTrue(a.getCreator().equals(user));
		Assert.isTrue(a.getMoment() != null);
		res = new FollowUp();
		res.setMarked(false);
		res.setArticle(a);
		final List<String> urls = new ArrayList<String>();
		res.setPictureUrls(urls);
		return res;
	}
	public FollowUp save(final FollowUp followUp) {
		assert followUp != null;
		FollowUp res;
		final User user = this.userService.findByPrincipal();
		Assert.isTrue(followUp.getArticle().getCreator().equals(user));
		Assert.isTrue(followUp.getArticle().getMoment() != null);
		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			taboow = followUp.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= followUp.getSummary().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= followUp.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		if (taboow)
			followUp.setMarked(true);
		final Date publicationDate = new Date(System.currentTimeMillis() - 10000);
		followUp.setMoment(publicationDate);
		res = this.followUpRepository.save(followUp);
		return res;
	}
	public void delete(final FollowUp followUp) {
		assert followUp != null;
		assert followUp.getId() != 0;
		final List<Admin> admins = new ArrayList<Admin>(this.adminService.findAll());
		Assert.isTrue(this.adminService.findByPrincipal().equals(admins.get(0)));
		Assert.isTrue(this.followUpRepository.findOne(followUp.getId()) != null);
		this.followUpRepository.delete(followUp.getId());

	}
	public Collection<FollowUp> findAll() {
		Collection<FollowUp> res;
		Assert.notNull(this.followUpRepository);
		res = this.followUpRepository.findAll();

		return res;
	}

	public FollowUp findOne(final int followUpId) {
		Assert.isTrue(followUpId != 0);
		FollowUp res;
		res = this.followUpRepository.findOne(followUpId);
		Assert.isTrue(res!=null);

		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<FollowUp> findFollowUpsByArticle(final int articleId) {
		final Collection<FollowUp> res = this.followUpRepository.findFollowUpsByArticle(articleId);
		return res;
	}
	public Collection<FollowUp> findFollowUpsByUser(final int userId) {
		final Collection<FollowUp> res = this.followUpRepository.findFollowUpsByUser(userId);
		return res;
	}
	public Collection<FollowUp> findMarkedFollowUpsByUser() {
		final Collection<FollowUp> res = this.followUpRepository.findMarkedFollowUps();
		return res;
	}
	public FollowUp reconstruct(final FollowUp followUp, final BindingResult binding) {
		FollowUp result;

		result = followUp;
		result.setMarked(false);
		final Date publicationDate = new Date(System.currentTimeMillis() - 10000);
		followUp.setMoment(publicationDate);

		this.validator.validate(result, binding);
		return result;
	}
}
