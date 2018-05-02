
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
import domain.FollowUp;

@Controller
@RequestMapping("/followUp")
public class FollowUpController extends AbstractController {

	@Autowired
	ArticleService	articleService;
	@Autowired
	FollowUpService	followUpService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int followUpId) {
		ModelAndView res;
		final FollowUp followUp = this.followUpService.findOne(followUpId);
		Assert.isTrue(followUp.getArticle().getNewspaper().isFree());
		final String requestURI = "followUp/display.do";
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
		Assert.isTrue(this.articleService.findOne(articleId).getNewspaper().isFree());
		final Collection<FollowUp> followUps = this.followUpService.findFollowUpsByArticle(articleId);
		final String requestURI = "followUp/list.do";
		res = new ModelAndView("followUp/list");
		res.addObject("followUps", followUps);
		res.addObject("requestURI", requestURI);

		return res;
	}

}
