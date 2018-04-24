
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer parentId) {
		final ModelAndView res = new ModelAndView("folder/list");
		if (parentId == null)
			res.addObject("folders", this.folderService.mainFolders());
		else {
			final Folder parent = this.folderService.findOne(parentId);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
			res.addObject("folders", parent.getChildren());
		}
		return res;

	}
}
