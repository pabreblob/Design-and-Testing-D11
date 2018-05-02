
package controllers;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import services.UserService;
import services.VolumeService;
import domain.Article;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Controller
@RequestMapping("/volume/user")
public class VolumeUserController extends AbstractController {

	@Autowired
	VolumeService	volumeService;
	@Autowired
	UserService		userService;
	@Autowired
	NewspaperService newspaperService;
	


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int volumeId) {
		ModelAndView res;
		User user=this.userService.findByPrincipal();
		boolean volumeCreated=false;
		final Volume volume = this.volumeService.findOne(volumeId);
		Assert.notNull(volume);
		if(volume.getCreator().getId()==user.getId()){
			volumeCreated=true;
		}
		final Collection<Newspaper> newspapers=volume.getNewspapers();
		final String requestURI = "volume/user/display.do";
		res = new ModelAndView("volume/display");
		res.addObject("volume", volume);
		res.addObject("volumeCreated", volumeCreated);
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/list-created", method = RequestMethod.GET)
	public ModelAndView listCreated(final HttpServletRequest request) {
		ModelAndView res;
		final User user=this.userService.findByPrincipal();
		final Collection<Volume> volumes = this.volumeService.findVolumesByCreator(user.getId());
		res = new ModelAndView("volume/list");
		res.addObject("volumes", volumes);
		res.addObject("requestURI", "volume/user/list-created.do");
		return res;
	}
	@RequestMapping(value = "/add-newspaper")
	public ModelAndView addNewspaper(@RequestParam final int volumeId,@RequestParam final int newspaperId) {
		ModelAndView res;
		Volume volume=this.volumeService.findOne(volumeId);
		Newspaper newspaper=this.newspaperService.findOne(newspaperId);
		this.volumeService.addNewspaper(volume, newspaper);
		res = new ModelAndView("redirect:/newspaper/user/list-available.do?volumeId="+volumeId);
		//debe dirigir a la lista de newspapers disponibles a añadir
		return res;
	}
	@RequestMapping(value = "/remove-newspaper")
	public ModelAndView removeNewspaper(@RequestParam final int volumeId,@RequestParam final int newspaperId) {
		Volume volume=this.volumeService.findOne(volumeId);
		Newspaper newspaper=this.newspaperService.findOne(newspaperId);
		this.volumeService.removeNewspaper(volume, newspaper);
		ModelAndView res = this.display(volumeId);
		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Volume v = this.volumeService.createVolume();
		res = this.createEditModelAndView(v);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int volumeId) {
		ModelAndView res;
		Volume v;
		v = this.volumeService.findOne(volumeId);
		Assert.isTrue(v.getCreator().getId() == this.userService.findByPrincipal().getId());
		res = this.createEditModelAndView(v);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Volume v, final BindingResult binding) {
		ModelAndView res;
		final Volume volume = this.volumeService.reconstruct(v, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(volume);
		else
			try {
				this.volumeService.save(volume);
				res = new ModelAndView("redirect:list-created.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(volume, "volume.commit.error");
			}
		return res;
	}
	//Ancillary methods
		protected ModelAndView createEditModelAndView(final Volume v) {
			return this.createEditModelAndView(v, null);
		}
		protected ModelAndView createEditModelAndView(final Volume v, final String messageCode) {
			ModelAndView res;
			res = new ModelAndView("volume/edit");
			res.addObject("volume", v);
			return res;
		}

}
