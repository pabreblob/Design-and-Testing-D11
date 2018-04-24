/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import services.SubscriptionService;
import domain.Newspaper;
import domain.Subscription;

@Controller
@RequestMapping("/subscription/customer")
public class SubscriptionCustomerController extends AbstractController {

	@Autowired
	SubscriptionService	subscriptionService;

	@Autowired
	NewspaperService	newspaperService;


	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(final int newspaperId) {
		ModelAndView result;
		final Newspaper n = this.newspaperService.findOne(newspaperId);
		Assert.isTrue(!n.isFree());
		Assert.isTrue(n.getPublicationDate() != null);
		Assert.isNull(this.subscriptionService.getSubscriptionByNewspaperAndPrincipal(newspaperId));
		final Subscription s = this.subscriptionService.create();
		s.setNewspaper(this.newspaperService.findOne(newspaperId));
		result = new ModelAndView("subscription/edit");
		result.addObject("subscription", s);
		result.addObject("newspaperUrl", "newspaper/display.do?newspaperId=" + newspaperId);
		result.addObject("requestURI", "subscription/customer/subscribe.do");
		return result;
	}
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Subscription s, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("subscription/edit");
			res.addObject("subscription", s);
		} else
			try {
				this.subscriptionService.save(s);
				Assert.isTrue(!s.isVolume());
				res = new ModelAndView("redirect:/newspaper/display.do?newspaperId=" + s.getNewspaper().getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("newspaper/edit");
				res.addObject("subscription", s);
				res.addObject("message", "newspaper.commit.error");
			}
		return res;
	}
}
