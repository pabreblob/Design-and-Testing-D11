
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/user")
public class NewspapersUserController extends AbstractController {

	@Autowired
	NewspaperService	newspaperService;

	@Autowired
	UserService			userService;

	@Autowired
	ArticleService		articleService;


	//	Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findNewspaperCreatedByPrincipal();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/user/list.do");
		return res;
	}

	//	Listing for add to volumen
	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam final int volumeId) {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findPublicatedNewspaperByPrincipal(volumeId);
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("volumeId", volumeId);
		res.addObject("requestURI", "newspaper/user/list-available.do");
		return res;
	}

	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Newspaper n = this.newspaperService.create();
		res = this.createEditModelAndView(n);

		return res;
	}

	//Editing
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int newspaperId) {
		ModelAndView res;
		Newspaper n;
		n = this.newspaperService.findOne(newspaperId);
		Assert.isTrue(n.getCreator().equals(this.userService.findByPrincipal()));
		Assert.isTrue(n.getPublicationDate() == null);
		res = this.createEditModelAndView(n);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Newspaper n, final BindingResult binding) {
		ModelAndView res;
		if (n.getPrice() == 0)
			n.setFree(true);
		else
			n.setFree(false);
		if (binding.hasErrors())
			res = this.createEditModelAndView(n);
		else
			try {
				this.newspaperService.save(n);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(n, "newspaper.commit.error");
			}
		return res;
	}

	//Publishing
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam final int newspaperId) {
		final ModelAndView res;
		Boolean allFinal = true;
		for (final Article a : this.articleService.findArticlesByNewspaper(newspaperId))
			if (a.isFinalMode() == false) {
				allFinal = false;
				break;
			}
		if (allFinal == false) {
			Collection<Newspaper> newspapers;
			newspapers = this.newspaperService.findNewspaperCreatedByPrincipal();
			res = new ModelAndView("newspaper/list");
			res.addObject("newspapers", newspapers);
			res.addObject("requestURI", "newspaper/user/list.do");
			res.addObject("message", "newspaper.articlesnotfinal");
		} else {
			this.newspaperService.publish(newspaperId);
			res = new ModelAndView("redirect:list.do");
		}
		return res;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Newspaper n) {
		return this.createEditModelAndView(n, null);
	}
	protected ModelAndView createEditModelAndView(final Newspaper n, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("newspaper/edit");
		res.addObject("newspaper", n);
		res.addObject("message", messageCode);
		return res;
	}
}
