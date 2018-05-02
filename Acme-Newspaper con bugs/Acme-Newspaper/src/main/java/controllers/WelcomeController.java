/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import services.UserService;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private UserService		userService;
	@Autowired
	private ChirpService	chirpService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		final ModelAndView result = new ModelAndView("welcome/index");
		SimpleDateFormat formatter;
		String moment;
		final Collection<Chirp> chirps = new ArrayList<Chirp>();

		try {
			final User principal = this.userService.findByPrincipal();
			chirps.addAll(this.chirpService.findByCreatorId(principal.getId()));
			for (final User f : principal.getFollowing())
				chirps.addAll(this.chirpService.findByCreatorId(f.getId()));
			if (!chirps.isEmpty())
				result.addObject("chirps", chirps);
		} catch (final Throwable oops) {

		}

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		String name = null;

		try {
			name = this.actorService.findByPrincipal().getUserAccount().getUsername();
		} catch (final Throwable oops) {

		}

		if (name == null)
			name = "John Doe";

		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}
}
