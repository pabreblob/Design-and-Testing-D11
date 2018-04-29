
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Admin;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository		adminRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private UserService			userService;
	@Autowired
	private NewspaperService	newspaperService;
	@Autowired
	private ArticleService		articleService;
	@Autowired
	private FollowUpService		followupService;
	@Autowired
	private ChirpService		chirpService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private SubscriptionService	subscriptionService;


	public AdminService() {
		super();
	}

	public Admin create() {
		Admin res;
		res = new Admin();
		final UserAccount ua = this.userAccountService.create();
		res.setUserAccount(ua);

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);

		return res;
	}
	public Admin save(final Admin admin) {
		Assert.notNull(admin);

		if (admin.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(admin.getUserAccount().getPassword(), null);
			admin.getUserAccount().setPassword(hash);
		}

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		authorities.add(auth);
		admin.getUserAccount().setAuthorities(authorities);
		final UserAccount ua = this.userAccountService.save(admin.getUserAccount());
		admin.setUserAccount(ua);
		final Admin res = this.adminRepository.save(admin);
		return res;
	}
	public Admin findOne(final int idAdmin) {
		Assert.isTrue(idAdmin != 0);
		final Admin res = this.adminRepository.findOne(idAdmin);
		return res;
	}

	public Collection<Admin> findAll() {
		final Collection<Admin> res;
		res = this.adminRepository.findAll();
		return res;
	}

	public Admin findByUserAccountId(final int adminAccountId) {
		Assert.isTrue(adminAccountId != 0);
		Admin res;
		res = this.adminRepository.findAdminByUserAccountId(adminAccountId);
		return res;
	}
	public Admin findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Admin res = this.adminRepository.findAdminByUserAccountId(u.getId());
		return res;
	}

	//DASBBOARD=============================================================================
	public double averageNewspaperPerUser() {
		final List<User> users = new ArrayList<>(this.userService.findAll());
		final List<Newspaper> newspapers = new ArrayList<>(this.newspaperService.findAll());
		double res = 0.;
		if (!(users.isEmpty() || newspapers.isEmpty()))
			res = newspapers.size() * 1.0 / users.size();
		return res;
	}
	public double standardDeviationNewspaperPerUser() {
		final double newspaperCreated = 1.0 * this.newspaperService.findAll().size();
		final double users = 1.0 * this.userService.findAll().size();
		final double avg = this.averageNewspaperPerUser();
		double res = 0.;
		double sum = 0.;
		int temp;
		for (final User u : this.userService.findAll()) {
			temp = this.newspaperService.findNewspaperCreatedByUserId(u.getId()).size();
			sum += (temp - avg) * (temp - avg);
		}
		if (!(newspaperCreated == 0 || users == 0))
			res = Math.sqrt(sum / (newspaperCreated - 1));
		return res;
	}
	public double averageArticlesPerWriter() {
		final List<User> writers = new ArrayList<>(this.adminRepository.listOfWriters());
		final List<Article> articles = new ArrayList<>(this.articleService.findAll());
		double res = 0.;
		if (!(writers.isEmpty() || articles.isEmpty()))
			res = articles.size() * 1.0 / writers.size();
		return res;
	}
	public double standardDeviationArticlesPerWriter() {
		final double articlesCreated = 1.0 * this.articleService.findAll().size();
		final double users = 1.0 * this.adminRepository.listOfWriters().size();
		final double avg = this.averageArticlesPerNewspaper();
		double res = 0.;
		double sum = 0.;
		int temp;
		for (final User u : this.adminRepository.listOfWriters()) {
			temp = this.adminRepository.findArticlesPerUser(u.getId()).size();
			sum += (temp - avg) * (temp - avg);
		}
		if (!(articlesCreated == 0 || users == 0))
			res = Math.sqrt(sum / (articlesCreated - 1));
		return res;
	}
	public double averageArticlesPerNewspaper() {
		final List<Article> articles = new ArrayList<>(this.articleService.findAll());
		final List<Newspaper> newspapers = new ArrayList<>(this.newspaperService.findAll());
		double res = 0.;
		if (!(articles.isEmpty() || newspapers.isEmpty()))
			res = articles.size() * 1.0 / newspapers.size();
		return res;
	}
	public double standardDeviationArticlesPerNewspaper() {
		final double articlesCreated = 1.0 * this.articleService.findAll().size();
		final double newspaperCreated = 1.0 * this.newspaperService.findAll().size();
		final double avg = this.averageArticlesPerNewspaper();
		double res = 0.;
		double sum = 0.;
		int temp;
		for (final Newspaper n : this.newspaperService.findAll()) {
			temp = this.articleService.findArticlesByNewspaper(n.getId()).size();
			sum += (temp - avg) * (temp - avg);
		}
		if (!(articlesCreated == 0 || newspaperCreated == 0))
			res = Math.sqrt(sum / (articlesCreated - 1));
		return res;
	}
	public List<List<Newspaper>> newspaperWithTenPercent() {
		final List<List<Newspaper>> res = new ArrayList<>();
		final List<Newspaper> tenPercentFewer = new ArrayList<>();
		final List<Newspaper> tenPercentMore = new ArrayList<>();
		final double avg = this.averageArticlesPerNewspaper();
		for (final Newspaper n : this.newspaperService.findAll()) {
			final int nArticles = this.articleService.findArticlesByNewspaper(n.getId()).size();
			if (nArticles > 1.1 * avg)
				tenPercentMore.add(n);
			if (nArticles < 0.9 * avg)
				tenPercentFewer.add(n);
		}
		res.add(0, tenPercentMore);
		res.add(1, tenPercentFewer);
		return res;
	}
	public double ratioOfUserWhoHasCreatedANewspaper() {
		final List<User> userWhoHasCreated = new ArrayList<>(this.adminRepository.listOfUserWhoCreatedNewspaper());

		final List<User> userWhoHasNot = new ArrayList<>(this.userService.findAll());
		userWhoHasNot.removeAll(userWhoHasCreated);
		if (userWhoHasNot.size() == 0)
			return 0.;
		else
			return (userWhoHasCreated.size() * 1.0) / (userWhoHasNot.size() * 1.0);
	}
	public double ratioOfUserWhoHasWrittenAnArticle() {
		final List<User> userWhoHasCreated = new ArrayList<>(this.adminRepository.listOfWriters());

		final List<User> userWhoHasNot = new ArrayList<>(this.userService.findAll());
		userWhoHasNot.removeAll(userWhoHasCreated);

		if (userWhoHasNot.size() == 0)
			return 0.;
		else
			return (userWhoHasCreated.size() * 1.0) / (userWhoHasNot.size() * 1.0);
	}
	public double averageFollowupPerArticle() {
		final List<FollowUp> followups = new ArrayList<>(this.followupService.findAll());
		final List<Article> articles = new ArrayList<>(this.articleService.findAll());

		if (followups.isEmpty() || articles.isEmpty())
			return 0.;
		else
			return followups.size() * 1.0 / articles.size();
	}
	public List<Double> averageFollowupPerArticleUpToWeek() {
		final List<Double> res = new ArrayList<>();
		final int articlesCreated = this.articleService.findAll().size();
		double oneWeek = 0;
		double twoWeek = 0;
		for (final FollowUp f : this.followupService.findAll()) {
			final DateTime momentNewspaper = new DateTime(f.getArticle().getNewspaper().getPublicationDate());
			final DateTime momentNewspaperOneWeek = momentNewspaper.plusDays(7);
			final DateTime momentNewspaperTwoWeek = momentNewspaper.plusDays(14);
			final DateTime momentFollowUp = new DateTime(f.getMoment());
			if (momentFollowUp.isBefore(momentNewspaperOneWeek) && momentFollowUp.isAfter(momentNewspaper))
				oneWeek++;
			if (momentFollowUp.isBefore(momentNewspaperTwoWeek) && momentFollowUp.isAfter(momentNewspaper))
				twoWeek++;
		}
		if (articlesCreated == 0) {
			res.add(0, 0.);
			res.add(1, 0.);
		} else {
			res.add(0, (oneWeek * 1.0) / articlesCreated);
			res.add(1, (twoWeek * 1.0) / articlesCreated);
		}
		return res;
	}
	private double averageChirpPerUser() {
		final double chirps = 1.0 * this.chirpService.findAll().size();
		final double users = 1.0 * this.userService.findAll().size();
		if (chirps == 0. || users == 0.)
			return 0.;
		else
			return chirps / users;
	}
	public double ratioUserWhoPostAbove75OfAverage() {
		double res = 0.;
		int userWhoHas = 0;
		int userWhoHasNot = 0;
		for (final User u : this.userService.findAll())
			if (this.chirpService.findByCreatorId(u.getId()).size() > 0.75 * this.averageChirpPerUser())
				userWhoHas++;
			else
				userWhoHasNot++;
		if (userWhoHasNot != 0)
			res = (1.0 * userWhoHas) / (1.0 * userWhoHasNot);
		return res;
	}
	public double ratioOfPublicNewspaperVersusPrivateNewspaper() {
		final Double ratio = this.adminRepository.ratioOfPublicNewspaperVersusPrivateNewspaper();
		if (ratio == null)
			return 0.;
		else
			return ratio;
	}
	public double averageNumberOfArticlesPerPrivateNewspaper() {
		final int articles = this.adminRepository.findArticlesFromPrivateNewspaper().size();
		final int privates = this.adminRepository.findPrivateNewspaper().size();
		if (articles == 0 || privates == 0)
			return 0.;
		else
			return (1.0 * articles) / (1.0 * privates);
	}
	public double averageNumberOfArticlesPerPublicNewspaper() {
		final int articles = this.adminRepository.findArticlesFromPublicNewspaper().size();
		final int publics = this.adminRepository.findPublicNewspaper().size();
		if (articles == 0 || publics == 0)
			return 0.;
		else
			return (1.0 * articles) / (1.0 * publics);
	}
	private double averageSubscribersPerPrivateNewspaper() {
		final int subscribers = this.subscriptionService.findAll().size();
		final int newspapers = this.customerService.findAll().size();
		if (subscribers == 0 || newspapers == 0)
			return 0.;
		else
			return (1.0 * subscribers) / (1.0 * newspapers);
	}
	public double ratioOfSubscribersPerPrivateNewspaperVersusAllCustomers() {
		final int cust = this.customerService.findAll().size();
		if (cust != 0)
			return (this.averageSubscribersPerPrivateNewspaper() * 1.0) / (cust * 1.0);
		else
			return 0.;
	}
	private double ratioPrivateVersusPublicPerPublisher(final int userId) {
		final Double res = this.adminRepository.ratioOfPrivateNewspaperVersusPublicNewspaper(userId);
		if (res == null)
			return 0.;
		else
			return res;
	}
	public double averageRatioOfPrivateVersusPublicNewspapersPerPublisher() {
		double ratioSum = 0.;
		final Collection<User> creators = this.adminRepository.listOfUserWhoCreatedNewspaper();
		for (final User u : creators)
			ratioSum += this.ratioPrivateVersusPublicPerPublisher(u.getId());
		if (creators.isEmpty())
			return 0.;
		else
			return ratioSum / creators.size();
	}

	public double ratioOfAdvertisedNewspaperVersusNotAdvertised() {
		final Collection<Newspaper> adv = this.adminRepository.findAdvertisedNewspapers();
		final Collection<Newspaper> notadv = this.newspaperService.findAll();
		notadv.removeAll(adv);
		Double res;

		if (adv.size() == 0 || notadv.size() == 0)
			res = 0.;
		else
			res = adv.size() * 1.0 / notadv.size();
		return res;
	}
	public double ratioOfMarkedAdvertisments() {
		Double res = this.adminRepository.ratioOfMarkedAdvertisments();
		if (res == null)
			res = 0.;
		return res;
	}

	public double averageNewspapersPerVolume() {
		Double res = this.adminRepository.averageNewspapersPerVolume();
		if (res == null)
			res = 0.;
		return res;
	}

	public double ratioOfSubscriptionsToNewspaperVersusVolumes() {
		Double res = this.adminRepository.ratioOfSubscriptionsToVolumesVersusNewspapers();
		if (res == null)
			res = 0.;
		return res;
	}
}
