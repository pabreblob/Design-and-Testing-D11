
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/admin")
public class ChirpAdminController extends AbstractController {

	@Autowired
	private ChirpService	chirpService;


	@RequestMapping(value = "/list-marked")
	public ModelAndView listMarked() {
		ModelAndView res;
		final Collection<Chirp> marked = this.chirpService.findMarked();
		final boolean listmarked = true;

		res = new ModelAndView("chirp/list-marked");
		res.addObject("marked", marked);
		res.addObject("listmarked", listmarked);

		return res;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Chirp> chirps = this.chirpService.findAll();

		res = new ModelAndView("chirp/list");
		res.addObject("chirps", chirps);

		return res;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int chirpId) {
		final Chirp c = this.chirpService.findOne(chirpId);
		final ModelAndView res = new ModelAndView("chirp/list");
		try {
			this.chirpService.delete(c);
			res.addObject("chirps", this.chirpService.findAll());
		} catch (final Throwable oops) {
			res.addObject("message", "chirp.error");
		}
		return res;
	}

	@RequestMapping(value = "/delete-marked")
	public ModelAndView deleteMarked(@RequestParam final int chirpId) {
		final Chirp c = this.chirpService.findOne(chirpId);
		final ModelAndView res = new ModelAndView("chirp/list-marked");
		try {
			this.chirpService.delete(c);
			res.addObject("marked", this.chirpService.findMarked());
		} catch (final Throwable oops) {
			res.addObject("message", "chirp.error");
		}
		return res;
	}

}
