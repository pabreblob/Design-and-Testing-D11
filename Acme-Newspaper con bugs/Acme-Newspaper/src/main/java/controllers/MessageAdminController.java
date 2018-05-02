
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message/admin")
public class MessageAdminController extends AbstractController {

	@Autowired
	private MessageService	messageService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("mess") final Message mess, final BindingResult binding) {
		ModelAndView result;
		final Message message = this.messageService.reconstructAdmin(mess, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				this.messageService.broadcast(message);
				final Folder outf = message.getFolder();
				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + outf.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("message/editBroadcast");
		result.addObject("mess", message);
		result.addObject("message", messageCode);
		return result;
	}
}
