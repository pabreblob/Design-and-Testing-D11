
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

import services.ArticleService;
import services.FollowUpService;
import services.NewspaperService;
import services.UserService;
import domain.Article;
import domain.FollowUp;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController extends AbstractController {

	@Autowired
	ArticleService		articleService;
	@Autowired
	UserService			userService;
	@Autowired
	NewspaperService	newspaperService;
	@Autowired
	FollowUpService		followUpService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int followUpId) {
		ModelAndView res;
		final FollowUp followUp = this.followUpService.findOne(followUpId);
		Assert.isTrue(followUp.getArticle().getNewspaper().isFree() || followUp.getArticle().getCreator().getId() == this.userService.findByPrincipal().getId());
		final String requestURI = "article/user/display.do";
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
		Assert.isTrue(this.articleService.findOne(articleId).getNewspaper().isFree() || this.articleService.findOne(articleId).getCreator().getId() == this.userService.findByPrincipal().getId());
		final Collection<FollowUp> followUps = this.followUpService.findFollowUpsByArticle(articleId);
		final String requestURI = "followUp/user/list.do";
		res = new ModelAndView("followUp/list");
		res.addObject("followUps", followUps);
		res.addObject("requestURI", requestURI);

		return res;
	}
	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int articleId) {
		final ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		Assert.isTrue(article.getCreator().getId() == this.userService.findByPrincipal().getId() && article.getMoment() != null);
		final FollowUp f = this.followUpService.createFollowUp(article);
		res = this.createEditModelAndView(f);

		return res;
	}
	//	//Editing

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final FollowUp f, final BindingResult binding) {
		ModelAndView res;
		Assert.isTrue(f.getArticle().getCreator().getId() == this.userService.findByPrincipal().getId() && f.getArticle().getMoment() != null);
		final FollowUp followUp = this.followUpService.reconstruct(f, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(followUp);
		else
			try {
				this.followUpService.save(followUp);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(followUp, "followUp.commit.error");
			}
		return res;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final FollowUp f) {
		return this.createEditModelAndView(f, null);
	}
	protected ModelAndView createEditModelAndView(final FollowUp f, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("followUp/edit");
		res.addObject("followUp", f);

		return res;
	}

}
