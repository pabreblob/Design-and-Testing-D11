
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.FollowUpService;
import services.SubscriptionService;
import domain.FollowUp;

@Controller
@RequestMapping("/followUp/customer")
public class FollowUpCustomerController extends AbstractController {

	@Autowired
	ArticleService		articleService;
	@Autowired
	SubscriptionService	subscriptionService;
	@Autowired
	FollowUpService		followUpService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int followUpId) {
		final ModelAndView res;
		final FollowUp followUp = this.followUpService.findOne(followUpId);
		Assert.isTrue(followUp.getArticle().getNewspaper().isFree() || this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(followUp.getArticle().getNewspaper().getId()) != null);
		final String requestURI = "followUp/customer/display.do";
		final List<String> pictures = new ArrayList<String>(followUp.getPictureUrls());
		final boolean hasPictures = !pictures.isEmpty();

		res = new ModelAndView("followUp/display");
		res.addObject("followUp", followUp);
		res.addObject("pictures", pictures);
		res.addObject("hasPictures", hasPictures);

		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int articleId) {
		ModelAndView res;
		Assert.isTrue(this.articleService.findOne(articleId).getNewspaper().isFree() || this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(this.articleService.findOne(articleId).getNewspaper().getId()) != null);
		final Collection<FollowUp> followUps = this.followUpService.findFollowUpsByArticle(articleId);
		final String requestURI = "followUp/customer/list.do";
		res = new ModelAndView("followUp/list");
		res.addObject("followUps", followUps);
		res.addObject("requestURI", requestURI);

		return res;
	}
}
