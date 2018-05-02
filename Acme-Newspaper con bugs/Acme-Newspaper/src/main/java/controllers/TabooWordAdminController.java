
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TabooWordService;
import domain.TabooWord;

@Controller
@RequestMapping("/tabooword/admin")
public class TabooWordAdminController extends AbstractController {

	@Autowired
	private TabooWordService	tabooWordService;


	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView res;
		final Collection<TabooWord> words = this.tabooWordService.findAll();

		res = new ModelAndView("tabooword/list");
		res.addObject("words", words);

		return res;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView res;
		final TabooWord word = this.tabooWordService.create();

		res = new ModelAndView("tabooword/create");
		res.addObject("word", word);

		return res;
	}

	@RequestMapping(value = "/create", params = "submit", method = RequestMethod.POST)
	public ModelAndView save(final TabooWord word, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = new ModelAndView("tabooword/create");
			res.addObject("word", word);
		} else
			try {
				this.tabooWordService.save(word);
				res = this.list();
			} catch (final Throwable oops) {
				res = new ModelAndView("tabooword/create");
				res.addObject("word", word);
				res.addObject("message", "tabooword.error");
			}

		return res;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int taboowordId) {
		final TabooWord word = this.tabooWordService.findOne(taboowordId);
		ModelAndView res;
		try {
			this.tabooWordService.delete(word);
			res = this.list();
		} catch (final Throwable oops) {
			res = this.list();
			res.addObject("message", "tabooword.error");
		}

		return res;
	}

}
