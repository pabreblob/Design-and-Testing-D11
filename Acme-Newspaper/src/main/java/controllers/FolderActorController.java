
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
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
			res.addObject("parent", null);
		} else {
			final Folder parent = this.folderService.findOne(parentId);
			Assert.notNull(parent);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
			res.addObject("folders", parent.getChildren());
			if (parent.getParent() == null)
				res.addObject("back", null);
			else
				res.addObject("back", parent.getParent().getId());
			res.addObject("main", false);
			res.addObject("parent", parentId);
		}
		return res;
	}

	private ModelAndView listWithMessage(final Integer parentId, final String message) {
		final ModelAndView res = this.list(parentId);
		res.addObject("message", message);
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final HttpServletRequest r) {
		final String parentId = r.getParameter("parent");
		final String nameFolder = r.getParameter("name");
		if (StringUtils.isEmpty(parentId)) {
			if (this.folderService.findFolderByNameAndActor(nameFolder) != null)
				return this.listWithMessage(null, "folder.errorNameInUse");
			else {
				this.folderService.createNewFolder(nameFolder);
				return this.list(null);
			}
		} else if (this.folderService.findFolderByNameAndActor(nameFolder) != null)
			return this.listWithMessage(null, "folder.errorNameInUse");
		else {
			final Folder parent = this.folderService.findOne(new Integer(parentId));
			Assert.notNull(parent);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
			final Folder f = this.folderService.create();
			f.setName(nameFolder);
			this.folderService.addChild(f, parent);
			return this.list(parent.getId());
		}
	}
}
