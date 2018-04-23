/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AbstractController {

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;
		if (oops.getMessage().equals("[Assertion failed] - this expression must be true"))
			result = new ModelAndView("misc/403");
		else {
			result = new ModelAndView("misc/panic");
			result.addObject("name", ClassUtils.getShortName(oops.getClass()));
			result.addObject("exception", oops.getMessage());
			result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));
		}
		return result;
	}

	@RequestMapping(value = "/misc/terms")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("misc/terms");

		return result;
	}
	@RequestMapping(value = "/misc/contact")
	public ModelAndView contact() {
		ModelAndView result;

		result = new ModelAndView("misc/contact");

		return result;
	}
	@RequestMapping(value = "/misc/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("misc/cookies");

		return result;
	}

}
