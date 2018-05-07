
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.ArticleService;
import services.FollowUpService;
import services.SubscriptionService;
import domain.Advertisement;
import domain.Article;

@Controller
@RequestMapping("/article/customer")
public class ArticleCustomerController extends AbstractController {

	@Autowired
	ArticleService		articleService;
	@Autowired
	SubscriptionService	subscriptionService;
	@Autowired
	FollowUpService		followUpService;
	@Autowired
	AdvertisementService	advertisementService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		final ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		Assert.isTrue(article.getNewspaper().isFree() || this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(article.getNewspaper().getId()) != null);
		final String requestURI = "article/customer/display.do";
		final List<String> pictures = new ArrayList<String>(article.getPictureUrls());
		final boolean hasPictures = !pictures.isEmpty();
		final boolean hasFollowUps = !this.followUpService.findFollowUpsByArticle(articleId).isEmpty();
		List<Advertisement> advs=new ArrayList<Advertisement>(this.advertisementService.findAdvertisementByNewspaperId(article.getNewspaper().getId()));
		int advertisementSize=advs.size();

		res = new ModelAndView("article/display");
		if(advertisementSize>0){
			String bannerUrl=this.advertisementService.getRandomAdvertisementImage(article.getNewspaper().getId());
			res.addObject("bannerUrl", bannerUrl);
		}
		res.addObject("advertisementSize", advertisementSize);
		res.addObject("article", article);
		res.addObject("pictures", pictures);
		res.addObject("hasPictures", hasPictures);
		res.addObject("hasFollowUps", hasFollowUps);
		res.addObject("requestURI", requestURI);

		return res;
	}
}
