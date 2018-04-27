
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import domain.Advertisement;

@Controller
@RequestMapping("/advertisement/admin")
public class AdvertisementAdminController extends AbstractController {

	@Autowired
	private AdvertisementService	advertisementService;


	@RequestMapping(value = "/list-marked")
	public ModelAndView listMarked() {
		final ModelAndView res = new ModelAndView("advertisement/list-marked");
		final Collection<Advertisement> marked = this.advertisementService.findMarked();

		res.addObject("marked", marked);
		return res;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("advertisement/list");
		final Collection<Advertisement> advs = this.advertisementService.findAll();

		res.addObject("advertisements", advs);
		return res;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int advertisementId) {
		final Advertisement a = this.advertisementService.findOne(advertisementId);
		final ModelAndView res = new ModelAndView("advertisement/list");
		try {
			this.advertisementService.delete(a);
			res.addObject("advertisement", this.advertisementService.findAll());
		} catch (final Throwable oops) {
			res.addObject("message", "advertisement.error");
		}

		return res;
	}

	@RequestMapping(value = "/delete-marked")
	public ModelAndView deleteMarked(@RequestParam final int advertisementId) {
		final Advertisement a = this.advertisementService.findOne(advertisementId);
		final ModelAndView res = new ModelAndView("advertisement/list-marked");
		try {
			this.advertisementService.delete(a);
			res.addObject("marked", this.advertisementService.findMarked());
		} catch (final Throwable oops) {
			res.addObject("message", "advertisement.error");
		}

		return res;
	}

}
