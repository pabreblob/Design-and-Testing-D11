
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.FollowUpService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

	@Autowired
	ArticleService	articleService;
	@Autowired
	FollowUpService	followUpService;


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		ModelAndView res;
		final Article article = this.articleService.findOne(articleId);
		Assert.notNull(article);
		Assert.isTrue(article.getNewspaper().isFree());
		final String requestURI = "article/display.do";
		final List<String> pictures = new ArrayList<String>(article.getPictureUrls());
		final boolean hasPictures = !pictures.isEmpty();
		final boolean hasFollowUps = !this.followUpService.findFollowUpsByArticle(articleId).isEmpty();

		res = new ModelAndView("article/display");
		res.addObject("article", article);
		res.addObject("hasFollowUps", hasFollowUps);
		res.addObject("pictures", pictures);
		res.addObject("hasPictures", hasPictures);
		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView res;
		res = new ModelAndView("article/search");
		return res;
	}

	@RequestMapping(value = "/list-search", method = RequestMethod.GET)
	public ModelAndView searchList(final HttpServletRequest request) {
		ModelAndView res;
		final String keyword = request.getParameter("keyword");
		final Collection<Article> articles = this.articleService.findArticlesByKeyword(keyword);
		res = new ModelAndView("article/list");
		res.addObject("articles", articles);
		res.addObject("requestURI", "article/list-search.do");
		return res;
	}

}
