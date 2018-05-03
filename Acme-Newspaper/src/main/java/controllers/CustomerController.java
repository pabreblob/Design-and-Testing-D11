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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.UserForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserForm userForm = new UserForm();

		result = new ModelAndView("customer/edit");
		result.addObject("userForm", userForm);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		final Customer customer = this.customerService.reconstruct(userForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(userForm);
			System.out.println("Error general");
			System.out.println(binding.getAllErrors());
		} else if (userForm.isAcceptTerms() == false) {
			result = this.createEditModelAndView(userForm, "user.notAccepted.error");
			System.out.println("No aceptado los términos");
		} else if (!(userForm.getConfirmPass().equals(userForm.getPassword()))) {
			result = this.createEditModelAndView(userForm, "user.differentPass.error");
			System.out.println("Contraseñas distintas");
		} else
			try {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(userForm, "user.commit.error");
				System.out.println("Error grave");
				System.out.println(oops.getMessage());
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(userForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("customer/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", messageCode);
		return result;
	}
}
