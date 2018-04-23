
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdminService;
import services.ArticleService;
import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import services.UserService;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	@Autowired
	NewspaperService	newspaperService;

	@Autowired
	UserService			userService;

	@Autowired
	CustomerService		customerService;

	@Autowired
	AdminService		adminService;

	@Autowired
	ArticleService		articleService;

	@Autowired
	SubscriptionService	subscriptionService;


	//	Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findPublicatedNewspaper();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/list.do");

		return res;
	}

	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		Boolean subscribed = true;
		Boolean customerLogged = false;
		Boolean somethingLogged = false;
		Boolean needPay = false;
		Boolean available = false;
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		Assert.notNull(newspaper);

		try {
			LoginService.getPrincipal().getId();
			somethingLogged = true;
		} catch (final Exception e) {
			// TODO: handle exception
		}
		try {
			if (this.customerService.findByPrincipal() != null)
				customerLogged = true;
			if (this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(newspaperId) == null)
				subscribed = false;

		} catch (final Exception e) {

		}

		if (newspaper.isFree()) {
			subscribed = true;
			somethingLogged = true;
		}

		//Comprobaciones para saber si lo puede ver o no.
		if (newspaper.getPublicationDate() != null)
			available = true;
		try {
			if (newspaper.getCreator().getUserAccount().getId() == LoginService.getPrincipal().getId())
				available = true;
		} catch (final Exception e) {

		}

		try {
			Assert.notNull(this.adminService.findByPrincipal());
			available = true;
		} catch (final Exception e) {

		}
		Assert.isTrue(available);

		if (customerLogged && !subscribed)
			needPay = true;

		final ModelAndView res;
		res = new ModelAndView("newspaper/display");
		res.addObject("newspaper", newspaper);
		res.addObject("requestURI", "newspaper/display.do");
		int pictureSize;
		if (newspaper.getPictureUrl() == null || newspaper.getPictureUrl().length() == 0)
			pictureSize = 0;
		else
			pictureSize = newspaper.getPictureUrl().length();
		res.addObject("pictureSize", pictureSize);
		res.addObject("subscribed", subscribed);
		res.addObject("articles", this.articleService.findArticlesByNewspaper(newspaperId));
		res.addObject("customerLogged", customerLogged);
		res.addObject("needPay", needPay);
		res.addObject("somethingLogged", somethingLogged);
		res.addObject("requestURI", "newspaper/display.do?newspaperId=" + newspaperId);
		return res;
	}
	//Searching
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView res;
		res = new ModelAndView("newspaper/search");
		return res;
	}

	@RequestMapping(value = "/list-search", method = RequestMethod.GET)
	public ModelAndView searchList(final HttpServletRequest request) {
		ModelAndView res;
		final String keyword = request.getParameter("keyword");
		final Collection<Newspaper> newspapers = this.newspaperService.findNewspapersByKeyword(keyword);
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/list-search.do");
		return res;
	}

}
