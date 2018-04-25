
package controllers;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.VolumeService;
import domain.Customer;

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
		res.addObject("requestURI", "volume/customer/list");
		return res;
	}

}
