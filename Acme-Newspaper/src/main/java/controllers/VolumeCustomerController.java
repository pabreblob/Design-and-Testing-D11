
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

import services.CustomerService;
import services.VolumeService;
import domain.Customer;
import domain.Newspaper;

import domain.Volume;

@Controller
@RequestMapping("/volume/customer")
public class VolumeCustomerController extends AbstractController {

	@Autowired
	VolumeService	volumeService;
	@Autowired
	CustomerService	customerService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		Customer customer=this.customerService.findByPrincipal();
		final Collection<Volume> volumes = this.volumeService.findAll();
		res = new ModelAndView("volume/list");
		res.addObject("volumes", volumes);
		res.addObject("customer", customer);
		res.addObject("requestURI", "volume/customer/list.do");
		return res;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int volumeId) {
		ModelAndView res;
		final Volume volume = this.volumeService.findOne(volumeId);
		Assert.notNull(volume);
		final Collection<Newspaper> newspapers=volume.getNewspapers();
		boolean volumeCreated=false;
		boolean sub=false;
		if(volume.getCustomers().contains(customerService.findByPrincipal())){
			sub=true;
		}
		final String requestURI = "volume/customer/display.do";
		res = new ModelAndView("volume/display");
		res.addObject("volume", volume);
		res.addObject("volumeCreated", volumeCreated);
		res.addObject("sub", sub);
		res.addObject("newspapers", newspapers);
		res.addObject("requestURI", requestURI);

		return res;
	}

}
