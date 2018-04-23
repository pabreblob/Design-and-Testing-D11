
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

	@Autowired
	private ChirpService	chirpService;


	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Chirp c = this.chirpService.create();

		res = new ModelAndView("chirp/create");
		res.addObject("chirp", c);

		return res;
	}

	//Saving

	@RequestMapping(value = "/create", params = "submit", method = RequestMethod.POST)
	public ModelAndView save(final Chirp chirp, final BindingResult binding) {
		ModelAndView res;
		final Chirp c = this.chirpService.reconstruct(chirp, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("chirp/create");
			res.addObject("chirp", c);
		} else
			try {
				this.chirpService.save(c);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("chirp/create");
				res.addObject("chirp", c);
				res.addObject("message", "chirp.error");
			}

		return res;
	}

}
