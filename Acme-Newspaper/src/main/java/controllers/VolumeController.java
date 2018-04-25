
package controllers;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.VolumeService;
import domain.Newspaper;
import domain.Volume;

@Controller
@RequestMapping("/volume")
public class VolumeController extends AbstractController {

	@Autowired
	VolumeService	volumeService;
	


	//	Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int volumeId) {
		ModelAndView res;
		final Volume volume = this.volumeService.findOne(volumeId);
		Assert.notNull(volume);
		final Collection<Newspaper> newspapers=volume.getNewspapers();
		boolean volumeCreated=false;
		final String requestURI = "volume/display.do";
		res = new ModelAndView("volume/display");
		res.addObject("volume", volume);
		res.addObject("volumeCreated", volumeCreated);
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", requestURI);

		return res;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView searchList(final HttpServletRequest request) {
		ModelAndView res;
		
		final Collection<Volume> volumes = this.volumeService.findAll();
		res = new ModelAndView("volume/list");
		res.addObject("volumes", volumes);
		res.addObject("requestURI", "volume/list");
		return res;
	}

}
