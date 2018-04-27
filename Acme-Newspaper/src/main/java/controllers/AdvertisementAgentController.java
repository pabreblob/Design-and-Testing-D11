
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.NewspaperService;
import domain.Advertisement;
import domain.Newspaper;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private NewspaperService		newspaperService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		final ModelAndView res = new ModelAndView("advertisement/edit");
		final Newspaper n = this.newspaperService.findOne(newspaperId);
		final Advertisement a = this.advertisementService.create(n);

		res.addObject("advertisement", a);

		return res;
	}

	@RequestMapping(value = "/edit", params = "submit", method = RequestMethod.POST)
	public ModelAndView save(final Advertisement adv, final BindingResult binding) {
		ModelAndView res;
		final Advertisement advertisement = this.advertisementService.reconstruct(adv, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("advertisement/edit");
			res.addObject("advertisement", adv);
			System.out.println("tengo el error aquí");
			System.out.println(binding);
		} else
			try {
				this.advertisementService.save(advertisement);
				res = new ModelAndView("redirect:/newspaper/agent/list.do");
				System.out.println("no hay ningún error");
			} catch (final Throwable oops) {
				res = new ModelAndView("advertisement/edit");
				res.addObject("advertisement", adv);
				res.addObject("message", "advertisement.error");
				System.out.println("errores en el catch");
			}

		return res;
	}

}
