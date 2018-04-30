
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.ArticleService;
import services.FollowUpService;
import services.NewspaperService;
import services.UserService;
import domain.Advertisement;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController {

	@Autowired
	ArticleService		articleService;
	@Autowired
	UserService			userService;
	@Autowired
	NewspaperService	newspaperService;
	@Autowired
	FollowUpService		followUpService;
	@Autowired
	AdvertisementService	advertisementService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		Assert.isTrue(article.getNewspaper().isFree() || article.getCreator().getId() == this.userService.findByPrincipal().getId()|| article.getNewspaper().getCreator().getId()== this.userService.findByPrincipal().getId());
		Assert.isTrue(article.getMoment()!=null|| article.getCreator().getId() == this.userService.findByPrincipal().getId()|| article.getNewspaper().getCreator().getId()== this.userService.findByPrincipal().getId());
		final String requestURI = "article/user/display.do";
		final List<String> pictures = new ArrayList<String>(article.getPictureUrls());
		final boolean hasPictures = !pictures.isEmpty();
		final boolean hasFollowUps = !this.followUpService.findFollowUpsByArticle(articleId).isEmpty();
		String bannerUrl=this.advertisementService.getRandomAdvertisementImage(article.getNewspaper().getId());
		List<Advertisement> advs=new ArrayList<Advertisement>(this.advertisementService.findAdvertisementByNewspaperId(article.getNewspaper().getId()));
		int advertisementSize=advs.size();

		res = new ModelAndView("article/display");
		res.addObject("bannerUrl", bannerUrl);
		res.addObject("advertisementSize", advertisementSize);
		res.addObject("article", article);
		res.addObject("pictures", pictures);
		res.addObject("hasPictures", hasPictures);
		res.addObject("hasFollowUps", hasFollowUps);
		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/list-editable", method = RequestMethod.GET)
	public ModelAndView listMarked() {
		ModelAndView res;
		final Collection<Article> articles = this.articleService.findEditableArticlesByUser();
		final String requestURI = "article/user/list-editable.do";
		res = new ModelAndView("article/list");
		res.addObject("articles", articles);
		res.addObject("requestURI", requestURI);

		return res;
	}
	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Article a = this.articleService.createArticle();
		res = this.createEditModelAndView(a);

		return res;
	}
	//	//Editing
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		ModelAndView res;
		Article a;
		a = this.articleService.findOne(articleId);
		Assert.isTrue(a.getCreator().getId() == this.userService.findByPrincipal().getId());
		Assert.isTrue(a.isFinalMode() == false);
		res = this.createEditModelAndView(a);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Article a, final BindingResult binding) {
		ModelAndView res;
		final Article article = this.articleService.reconstruct(a, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(article);
		else
			try {
				this.articleService.save(article);
				res = new ModelAndView("redirect:list-editable.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(article, "article.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "/list-published", method = RequestMethod.GET)
	public ModelAndView listPublished() {
		ModelAndView res;
		final Collection<Article> articles = this.articleService.findPublishedArticlesByUser(this.userService.findByPrincipal().getId());
		final String requestURI = "article/user/list-published.do";
		res = new ModelAndView("article/list");
		res.addObject("articles", articles);
		res.addObject("requestURI", requestURI);

		return res;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Article a) {
		return this.createEditModelAndView(a, null);
	}
	protected ModelAndView createEditModelAndView(final Article a, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("article/edit");
		final Collection<Newspaper> newspapers = this.newspaperService.findNotPublicatedNewspaper();
		res.addObject("article", a);
		res.addObject("newspapers", newspapers);

		return res;
	}

}
