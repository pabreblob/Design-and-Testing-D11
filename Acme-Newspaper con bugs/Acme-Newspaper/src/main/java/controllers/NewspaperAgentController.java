
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/agent")
public class NewspaperAgentController extends AbstractController {

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
		newspapers = this.newspaperService.findNewspapersWithAdvertisementByAgent();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/agent/list.do");
		return res;
	}

	//	Listing without Adverts
	@RequestMapping(value = "/list-without", method = RequestMethod.GET)
	public ModelAndView listWithoutAdverts() {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findNewspapersWithoutAdvertisementByAgent();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/agent/list-without.do");
		return res;
	}

}
