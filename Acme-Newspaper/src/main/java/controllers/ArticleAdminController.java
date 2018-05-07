
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

import services.AdvertisementService;
import services.ArticleService;
import services.FollowUpService;
import domain.Advertisement;
import domain.Article;

@Controller
@RequestMapping("/article/admin")
public class ArticleAdminController extends AbstractController {

	@Autowired
	ArticleService	articleService;
	@Autowired
	FollowUpService	followUpService;
	@Autowired
	AdvertisementService	advertisementService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		final ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		final String requestURI = "article/admin/display.do";
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
		res.addObject("hasFollowUps", hasFollowUps);
		res.addObject("pictures", pictures);
		res.addObject("hasPictures", hasPictures);
		res.addObject("requestURI", requestURI);

		return res;
	}
	//	Listing
	@RequestMapping(value = "/list-marked", method = RequestMethod.GET)
	public ModelAndView listMarked() {
		ModelAndView res;
		final Collection<Article> articles = this.articleService.findMarkedArticlesByUser();
		final String requestURI = "article/admin/list-marked.do";
		res = new ModelAndView("article/list");
		res.addObject("articles", articles);
		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Article> articles = this.articleService.findAll();
		final String requestURI = "article/admin/list.do";
		res = new ModelAndView("article/list");
		res.addObject("articles", articles);
		res.addObject("requestURI", requestURI);

		return res;
	}

	//	Deleting
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int articleId) {
		ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		Assert.notNull(article);
		this.articleService.delete(article);
		res = new ModelAndView("redirect:list.do");

		return res;
	}

}
