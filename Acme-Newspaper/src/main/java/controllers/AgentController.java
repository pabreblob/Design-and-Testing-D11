
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AgentService;
import domain.Agent;
import forms.UserForm;

@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController {

	@Autowired
	private AgentService	agentService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("agent/edit");
		final UserForm uf = new UserForm();

		res.addObject("userForm", uf);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		final Agent agent = this.agentService.reconstruct(userForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(userForm);
		else if (userForm.isAcceptTerms() == false)
			result = this.createEditModelAndView(userForm, "user.notAccepted.error");
		else if (!(userForm.getConfirmPass().equals(userForm.getPassword())))
			result = this.createEditModelAndView(userForm, "user.differentPass.error");
		else
			try {
				this.agentService.save(agent);
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
		result = new ModelAndView("agent/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", messageCode);
		return result;
	}

}
