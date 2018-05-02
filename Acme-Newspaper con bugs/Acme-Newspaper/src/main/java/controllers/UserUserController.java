
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	@Autowired
	private UserService	userService;


	@RequestMapping(value = "followers", method = RequestMethod.GET)
	public ModelAndView listFollowers() {
		ModelAndView result;
		final Collection<User> followers = this.userService.findByPrincipal().getFollowers();
		result = new ModelAndView("user/followers");
		result.addObject("users", followers);
		result.addObject("requestURI", "user/user/followers.do");
		return result;
	}

	@RequestMapping(value = "following", method = RequestMethod.GET)
	public ModelAndView listFollowing() {
		ModelAndView result;
		final Collection<User> following = this.userService.findByPrincipal().getFollowing();
		result = new ModelAndView("user/following");
		result.addObject("users", following);
		result.addObject("unfollow", true);
		result.addObject("requestURI", "user/user/following.do");
		return result;
	}

	@RequestMapping(value = "unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam final int userId) {
		ModelAndView result;
		try {
			final User user = this.userService.findOne(userId);
			this.userService.unfollow(this.userService.findByPrincipal(), user);
			result = new ModelAndView("redirect:/user/user/following.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/user/user/following.do");
		}
		return result;
	}

	@RequestMapping(value = "follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam final int userId) {
		ModelAndView result;
		try {
			final User user = this.userService.findOne(userId);
			this.userService.follow(this.userService.findByPrincipal(), user);
			result = new ModelAndView("redirect:/user/user/following.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/user/user/following.do");
		}
		return result;
	}
}
