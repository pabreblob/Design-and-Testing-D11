
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
		if (parentId == null) {
			res.addObject("folders", this.folderService.mainFolders());
			res.addObject("back", null);
			res.addObject("main", true);
		} else {
			final Folder parent = this.folderService.findOne(parentId);
			Assert.notNull(parent);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
			res.addObject("folders", parent.getChildren());
			res.addObject("back", parent.getParent());
			res.addObject("main", false);
		}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		final Folder f = this.folderService.findOne(folderId);
		Assert.notNull(f);
		Assert.notNull(this.actorService.findByPrincipal().getFolders().contains(f));
		final Folder parent = f.getParent();
		this.folderService.delete(f);
		if (parent == null)
			return this.list(null);
		else
			return this.list(parent.getId());
	}
}
