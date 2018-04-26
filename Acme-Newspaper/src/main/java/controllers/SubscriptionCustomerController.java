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

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import services.VolumeService;
import domain.Newspaper;
import domain.Subscription;
import domain.Volume;
import forms.SubscriptionForm;

@Controller
@RequestMapping("/subscription/customer")
public class SubscriptionCustomerController extends AbstractController {

	@Autowired
	SubscriptionService	subscriptionService;

	@Autowired
	NewspaperService	newspaperService;

	@Autowired
	VolumeService		volumeService;
	@Autowired
	CustomerService		customerService;


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
		} else if (SubscriptionCustomerController.checkDate(s.getCreditCard().getExpMonth(), s.getCreditCard().getExpYear()) == false) {
			res = new ModelAndView("subscription/edit");
			res.addObject("subscription", s);
			res.addObject("message", "subscription.invalidcc");
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
	@RequestMapping(value = "/subscribeVolume", method = RequestMethod.GET)
	public ModelAndView subscribeVolume(final int volumeId) {
		ModelAndView result;
		final SubscriptionForm s = new SubscriptionForm();
		final Volume v = this.volumeService.findOne(volumeId);
		Assert.notNull(v);
		Assert.isTrue(!v.getCustomers().contains(this.customerService.findByPrincipal()));
		s.setVolume(v);
		result = new ModelAndView("subscription/subscribevolume");
		result.addObject("subscriptionForm", s);
		result.addObject("requestURI", "subscription/customer/subscribeVolume.do");
		return result;
	}
	@RequestMapping(value = "/subscribeVolume", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SubscriptionForm s, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("subscription/subscribevolume");
			res.addObject("subscriptionForm", s);
		} else if (SubscriptionCustomerController.checkDate(s.getCreditCard().getExpMonth(), s.getCreditCard().getExpYear()) == false) {
			res = new ModelAndView("subscription/subscribevolume");
			res.addObject("subscriptionForm", s);
			res.addObject("message", "subscription.invalidcc");
		} else
			try {
				Assert.isTrue(!s.getVolume().getCustomers().contains(this.customerService.findByPrincipal()));
				this.subscriptionService.subscribeVolume(s);
				res = new ModelAndView("redirect:/volume/display.do?volumeId=" + s.getVolume().getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("newspaper/subscribevolume");
				res.addObject("subscriptionForm", s);
				res.addObject("message", "newspaper.commit.error");
			}

		return res;
	}

	private static boolean checkDate(final int month, final int year) {
		boolean res = false;
		final Date now = new Date();
		if (now.getYear() - 100 < year)
			res = true;
		if (now.getYear() - 100 == year && now.getMonth() + 1 <= month)
			res = true;

		return res;
	}

}
