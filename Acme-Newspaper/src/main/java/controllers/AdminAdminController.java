
package controllers;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import domain.Newspaper;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController {

	@Autowired
	AdminService	adminService;


	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView res = new ModelAndView("admin/dashboard");
		final DecimalFormat df = new DecimalFormat("#,###,##0.00");

		res.addObject("averageNewspaperPerUser", df.format(this.adminService.averageNewspaperPerUser()));
		res.addObject("standardDeviationNewspaperPerUser", df.format(this.adminService.standardDeviationNewspaperPerUser()));
		res.addObject("averageArticlesPerWriters", df.format(this.adminService.averageArticlesPerWriter()));
		res.addObject("standardDeviationArticlesPerWriters", df.format(this.adminService.standardDeviationArticlesPerWriter()));
		res.addObject("averageArticlePerNewspaper", df.format(this.adminService.averageArticlesPerNewspaper()));
		res.addObject("standardDeviationArticlePerNewspaper", df.format(this.adminService.standardDeviationArticlesPerNewspaper()));
		final List<List<Newspaper>> newspapersLists = this.adminService.newspaperWithTenPercent();
		res.addObject("newspaperWithArticlesTenPercentMoreThanAverage", newspapersLists.get(0));
		res.addObject("newspaperWithArticlesTenPercentFewerThanAverage", newspapersLists.get(1));
		res.addObject("ratioUsersWhoHasCreatedANewspaper", df.format(this.adminService.ratioOfUserWhoHasCreatedANewspaper()));
		res.addObject("ratioUsersWhoHasWrittenAnArticle", df.format(this.adminService.ratioOfUserWhoHasWrittenAnArticle()));
		res.addObject("averageFollowUpPerArticle", df.format(this.adminService.averageFollowupPerArticle()));
		final List<Double> averageFollowUps = this.adminService.averageFollowupPerArticleUpToWeek();
		res.addObject("averageFollowUpPerArticleUpToOneWeek", df.format(averageFollowUps.get(0)));
		res.addObject("averageFollowUpPerArticleUpToTwoWeek", df.format(averageFollowUps.get(1)));
		res.addObject("ratioUserWhoPostAbove75OfAverage", df.format(this.adminService.ratioUserWhoPostAbove75OfAverage()));
		res.addObject("ratioOfPublicNewspaperVersusPrivateNewspaper", df.format(this.adminService.ratioOfPublicNewspaperVersusPrivateNewspaper()));
		res.addObject("averageNumberOfArticlesPerPrivateNewspaper", df.format(this.adminService.averageNumberOfArticlesPerPrivateNewspaper()));
		res.addObject("averageNumberOfArticlesPerPublicNewspaper", df.format(this.adminService.averageNumberOfArticlesPerPublicNewspaper()));
		res.addObject("ratioOfSubscribersVersusAllCustomers", df.format(this.adminService.ratioOfSubscribersPerPrivateNewspaperVersusAllCustomers()));
		res.addObject("averageRatioOfPrivateVersusPublicNewspapersPerPublisher", df.format(this.adminService.averageRatioOfPrivateVersusPublicNewspapersPerPublisher()));
		return res;
	}
}
