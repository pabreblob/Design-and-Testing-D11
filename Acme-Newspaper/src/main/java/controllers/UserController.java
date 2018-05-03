
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.ChirpService;
import services.UserService;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService		userService;
	@Autowired
	private ChirpService	chirpService;
	@Autowired
	private ArticleService	articleService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestURI", "user/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserForm userForm = new UserForm();

		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		final User user = this.userService.reconstruct(userForm, binding);
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
				this.userService.save(user);
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
		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer userId) {
		final ModelAndView result;
		User principal = null;
		try {
			principal = this.userService.findByPrincipal();
		} catch (final Throwable oops) {
		}

		if (userId == null && principal == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			if (userId != null && this.userService.findOne(userId) == null)
				return new ModelAndView("redirect:/welcome/index.do");
			result = new ModelAndView("user/display");

			if (userId != null && principal == null) {
				result.addObject("user", this.userService.findOne(userId));
				result.addObject("articles", this.articleService.findPublishedArticlesByUser(userId));
				result.addObject("chirps", this.chirpService.findByCreatorId(userId));
				result.addObject("following", null);
				return result;
			} else if (userId == null && principal != null) {
				result.addObject("user", principal);
				result.addObject("articles", this.articleService.findPublishedArticlesByUser(principal.getId()));
				result.addObject("chirps", this.chirpService.findByCreatorId(principal.getId()));
				result.addObject("following", null);
				return result;
			} else {
				final User u = this.userService.findOne(userId);
				result.addObject("user", u);
				result.addObject("articles", this.articleService.findPublishedArticlesByUser(userId));
				result.addObject("chirps", this.chirpService.findByCreatorId(userId));
				if (userId != principal.getId()) {
					if (principal.getFollowing().contains(u))
						result.addObject("following", true);
					else
						result.addObject("following", false);
				} else
					result.addObject("following", null);
			}
		}
		return result;
	}
}
