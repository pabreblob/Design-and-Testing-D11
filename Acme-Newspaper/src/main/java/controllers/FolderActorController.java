
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import domain.Folder;
import forms.FolderMoveForm;

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
			res.addObject("name", null);
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
			res.addObject("name", parent.getName());
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
			else if (nameFolder.replace(" ", "").length() == 0)
				return this.listWithMessage(null, "folder.errorNameBlank");
			else
				try {
					this.folderService.createNewFolder(nameFolder);
					return this.list(null);
				} catch (final Throwable oops) {
					return this.listWithMessage(null, "folder.cannotCommit");
				}
		} else if (this.folderService.findFolderByNameAndActor(nameFolder) != null)
			return this.listWithMessage(new Integer(parentId), "folder.errorNameInUse");
		else if (nameFolder.replace(" ", "").length() == 0)
			return this.listWithMessage(new Integer(parentId), "folder.errorNameBlank");
		else
			try {
				final Folder parent = this.folderService.findOne(new Integer(parentId));
				Assert.notNull(parent);
				Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
				final Folder f = this.folderService.create();
				f.setName(nameFolder);
				this.folderService.addChild(f, parent);
				return this.list(parent.getId());
			} catch (final Throwable oops) {
				return this.listWithMessage(new Integer(parentId), "folder.cannotCommit");
			}
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		final Folder f = this.folderService.findOne(folderId);
		Assert.notNull(f);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		Assert.isTrue(!f.getName().equals("In box"));
		Assert.isTrue(!f.getName().equals("Out box"));
		Assert.isTrue(!f.getName().equals("Spam box"));
		Assert.isTrue(!f.getName().equals("Trash box"));
		Assert.isTrue(!f.getName().equals("Notification box"));
		final ModelAndView res = new ModelAndView("folder/edit");
		res.addObject("folder", f);
		return res;
	}

	private ModelAndView editWithMessage(final int folderId, final String message) {
		final ModelAndView e = this.edit(folderId);
		e.addObject("message", message);
		return e;
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	public ModelAndView editSave(final Folder folder, final BindingResult br) {
		final Folder f = this.folderService.reconstruct(folder, br);
		if (br.hasErrors())
			return this.editWithMessage(folder.getId(), "folder.cannotCommit");
		try {
			this.folderService.saveRename(f);
			if (f.getParent() == null)
				return this.list(null);
			else
				return this.list(f.getParent().getId());
		} catch (final Throwable oops) {
			if (f.getName().replace(" ", "").length() == 0)
				return this.editWithMessage(f.getId(), "folder.errorNameBlank");
			else if (this.folderService.findFolderByNameAndActor(f.getName()) != null)
				return this.editWithMessage(f.getId(), "folder.errorNameInUse");
			else
				return this.editWithMessage(f.getId(), "folder.cannotCommit");
		}
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(final int folderId) {
		final Folder toMove = this.folderService.findOne(folderId);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(toMove));
		Assert.notNull(toMove);
		final ModelAndView m = new ModelAndView("folder/move");
		m.addObject("toMove", toMove);

		final FolderMoveForm fmf = new FolderMoveForm();
		fmf.setFolderToMove(toMove.getId());
		m.addObject("moveForm", fmf);

		final Collection<Folder> toShow = this.actorService.findByPrincipal().getFolders();
		toShow.removeAll(this.folderService.allFoldersFromFolder(toMove));
		if (toMove.getParent() != null)
			toShow.remove(toMove.getParent());
		m.addObject("show", toShow);

		return m;
	}
	@RequestMapping(value = "/moveSave", method = RequestMethod.POST)
	public ModelAndView moveSave(final FolderMoveForm fmf) {
		try {
			final Folder toMove = this.folderService.recontructToMove(fmf);
			final Folder newParent = this.folderService.reconstructNewParent(fmf);
			this.folderService.changeParent(toMove, newParent);
			if (newParent == null)
				return this.list(null);
			else
				return this.list(newParent.getId());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect: list.do");
		}
	}
}
