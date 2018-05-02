
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/admin")
public class NewspaperAdminController extends AbstractController {

	@Autowired
	NewspaperService	newspaperService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findAll();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/admin/list.do");
		return res;
	}

	@RequestMapping(value = "/list-marked", method = RequestMethod.GET)
	public ModelAndView listMarked() {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findMarkedNewspaper();
		res = new ModelAndView("newspaper/list");
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", "newspaper/admin/list-marked.do");
		return res;
	}

	//	Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int newspaperId, final String requestURI) {
		ModelAndView res;
		final String redirect1 = "redirect:list.do";
		final String redirect2 = "redirect:list-marked.do";
		System.out.println(requestURI);
		try {
			this.newspaperService.delete(newspaperId);
			if (requestURI.equals("newspaper/admin/list-marked.do"))
				res = new ModelAndView(redirect2);
			else
				res = new ModelAndView(redirect1);
		} catch (final Exception e) {
			if (requestURI == "newspaper/admin/list-marked.do")
				res = new ModelAndView(redirect2);
			else
				res = new ModelAndView(redirect1);
			res.addObject("message", "newspaper.commit.error");
		}
		return res;
	}
}
