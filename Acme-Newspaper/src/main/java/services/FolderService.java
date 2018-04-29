
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.FolderMoveForm;

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository	folderRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;
	@Autowired
	private MessageService		messageService;


	public Folder create() {
		final Folder res = new Folder();

		final Collection<Folder> children = new ArrayList<>();
		final Collection<Message> messages = new ArrayList<>();

		res.setChildren(children);
		res.setMessages(messages);
		res.setParent(null);

		return res;
	}
	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!StringUtils.isEmpty(folder.getName()));

		final Folder f = this.findFolderByNameAndActor(folder.getName());
		if (f != null)
			Assert.isTrue(f.getId() == folder.getId());

		Assert.isTrue(!folder.getName().equals("In box"));
		Assert.isTrue(!folder.getName().equals("Out box"));
		Assert.isTrue(!folder.getName().equals("Spam box"));
		Assert.isTrue(!folder.getName().equals("Trash box"));
		Assert.isTrue(!folder.getName().equals("Notification box"));

		final Folder saved = this.folderRepository.save(folder);
		if (folder.getId() != 0)
			this.actorService.findByPrincipal().getFolders().remove(folder);
		this.actorService.findByPrincipal().getFolders().add(saved);

		return saved;
	}
	public Collection<Folder> defaultFolders() {
		final Collection<Folder> res = new ArrayList<>();

		Folder inbox = this.create();
		inbox.setName("In box");
		inbox = this.folderRepository.save(inbox);
		res.add(inbox);

		Folder outbox = this.create();
		outbox.setName("Out box");
		outbox = this.folderRepository.save(outbox);
		res.add(outbox);

		Folder spambox = this.create();
		spambox.setName("Spam box");
		spambox = this.folderRepository.save(spambox);
		res.add(spambox);

		Folder trashbox = this.create();
		trashbox.setName("Trash box");
		trashbox = this.folderRepository.save(trashbox);
		res.add(trashbox);

		Folder notificationbox = this.create();
		notificationbox.setName("Notification box");
		notificationbox = this.folderRepository.save(notificationbox);
		res.add(notificationbox);

		return res;
	}
	public void delete(final Folder f) {
		Assert.notNull(f);
		Assert.isTrue(!f.getName().equals("In box"));
		Assert.isTrue(!f.getName().equals("Out box"));
		Assert.isTrue(!f.getName().equals("Spam box"));
		Assert.isTrue(!f.getName().equals("Notification box"));
		Assert.isTrue(!f.getName().equals("Trash box"));
		Assert.isTrue(f.getChildren().isEmpty());
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));

		this.actorService.findByPrincipal().getFolders().remove(f);

		if (!f.getMessages().isEmpty()) {
			final Collection<Message> messages = new ArrayList<>(f.getMessages());
			for (final Message message : messages)
				this.messageService.delete(message);
		}
		this.folderRepository.delete(f);
	}
	public Folder createNewFolder(final String name) {
		Assert.notNull(name);
		Assert.isTrue(!StringUtils.isEmpty(name));
		Assert.isTrue(name.replace(" ", "").length() != 0);
		Assert.isTrue(!name.equals("In box"));
		Assert.isTrue(!name.equals("Out box"));
		Assert.isTrue(!name.equals("Spam box"));
		Assert.isTrue(!name.equals("Trash box"));
		Assert.isTrue(!name.equals("Notification box"));
		final Folder f = this.findFolderByNameAndActor(name);
		Assert.isNull(f);
		final Folder res = this.create();
		res.setName(name);
		final Folder saved = this.folderRepository.save(res);
		this.actorService.findByPrincipal().getFolders().add(saved);
		return saved;
	}
	public void addChild(final Folder child, final Folder parent) {
		Assert.notNull(child);
		Assert.isTrue(!StringUtils.isEmpty(child.getName()));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
		Assert.isTrue(child.getName().replace(" ", "").length() != 0);
		final Folder f = this.findFolderByNameAndActor(child.getName());
		Assert.isNull(f);

		Assert.isTrue(!child.getName().equals("In box"));
		Assert.isTrue(!child.getName().equals("Out box"));
		Assert.isTrue(!child.getName().equals("Spam box"));
		Assert.isTrue(!child.getName().equals("Trash box"));
		Assert.isTrue(!child.getName().equals("Notification box"));

		child.setParent(parent);

		final Folder saved = this.folderRepository.save(child);
		this.actorService.findByPrincipal().getFolders().add(saved);
		parent.getChildren().add(saved);
	}
	public Folder reconstruct(final Folder folder, final BindingResult br) {
		Folder res;
		if (folder.getId() == 0) {
			res = folder;
			final Collection<Folder> children = new ArrayList<>();
			final Collection<Message> messages = new ArrayList<>();
			res.setChildren(children);
			res.setMessages(messages);
			res.setParent(null);
		} else {
			final Folder f = this.findOne(folder.getId());
			res = this.create();
			res.setChildren(f.getChildren());
			res.setId(f.getId());
			res.setVersion(f.getVersion());
			res.setMessages(f.getMessages());
			res.setParent(f.getParent());
			res.setName(folder.getName());
		}
		this.validator.validate(res, br);
		return res;
	}
	public Folder saveRename(final Folder f) {
		final Folder old = this.findOne(f.getId());
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(old));
		Assert.isNull(this.findFolderByNameAndActor(f.getName()));
		Assert.isTrue(!f.getName().equals("In box"));
		Assert.isTrue(!f.getName().equals("Out box"));
		Assert.isTrue(!f.getName().equals("Spam box"));
		Assert.isTrue(!f.getName().equals("Trash box"));
		Assert.isTrue(!f.getName().equals("Notification box"));
		Assert.isTrue(!old.getName().equals("In box"));
		Assert.isTrue(!old.getName().equals("Out box"));
		Assert.isTrue(!old.getName().equals("Spam box"));
		Assert.isTrue(!old.getName().equals("Trash box"));
		Assert.isTrue(!old.getName().equals("Notification box"));
		Assert.isTrue(f.getName().replace(" ", "").length() != 0);

		return this.folderRepository.save(f);
	}
	public void changeParent(final Folder toMove, final Folder newParent) {
		Assert.notNull(toMove);
		if (newParent != null)
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(newParent));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(toMove));
		Assert.isTrue(!toMove.getName().equals("In box"));
		Assert.isTrue(!toMove.getName().equals("Out box"));
		Assert.isTrue(!toMove.getName().equals("Spam box"));
		Assert.isTrue(!toMove.getName().equals("Trash box"));
		Assert.isTrue(!toMove.getName().equals("Notification box"));
		if (newParent != null)
			Assert.isTrue(!this.checkIfIsChild(toMove, newParent));
		if (toMove.getParent() != null)
			toMove.getParent().getChildren().remove(toMove);
		if (newParent == null)
			toMove.setParent(null);
		else {
			toMove.setParent(newParent);
			newParent.getChildren().add(toMove);
		}
	}
	private boolean checkIfIsChild(final Folder toMove, final Folder newParent) {
		Boolean res = false;
		Folder checking = newParent;
		while (checking.getParent() != null)
			if (checking.getParent().getId() == toMove.getId()) {
				res = true;
				break;
			} else
				checking = checking.getParent();
		return res;
	}
	public Collection<Folder> findFolderByPrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return a.getFolders();
	}
	public Folder findFolderByNameAndActor(final String s) {
		return this.folderRepository.findFolderByNameAndActor(this.actorService.findByPrincipal().getId(), s);
	}
	public Folder findOne(final int id) {
		return this.folderRepository.findOne(id);
	}
	public Collection<Folder> mainFolders() {
		return this.folderRepository.mainFolders(this.actorService.findByPrincipal().getId());
	}
	public Collection<Folder> findAll() {
		return this.folderRepository.findAll();
	}
	public Collection<Folder> allFoldersFromFolder(final Folder f) {
		final Collection<Folder> toStart = new ArrayList<Folder>();
		toStart.add(f);
		final Set<Folder> res = new HashSet<>();
		this.allFoldersFromFolder(res, toStart);
		return res;
	}
	private Set<Folder> allFoldersFromFolder(final Set<Folder> res, final Collection<Folder> folders) {
		for (final Folder f : folders)
			if (f.getChildren().isEmpty())
				res.add(f);
			else {
				this.allFoldersFromFolder(res, f.getChildren());
				res.add(f);
			}
		return res;
	}
	public Folder reconstructNewParent(final FolderMoveForm fmf) {
		final Integer newParent = fmf.getIdNewParent();
		if (newParent == 0)
			return null;
		else {
			final Folder parent = this.findOne(newParent);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(parent));
			return parent;
		}
	}
	public Folder recontructToMove(final FolderMoveForm fmf) {
		final Integer toMoveId = fmf.getFolderToMove();
		Assert.isTrue(toMoveId != 0);
		final Folder f = this.findOne(toMoveId);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		Assert.isTrue(!f.getName().equals("In box"));
		Assert.isTrue(!f.getName().equals("Out box"));
		Assert.isTrue(!f.getName().equals("Spam box"));
		Assert.isTrue(!f.getName().equals("Trash box"));
		Assert.isTrue(!f.getName().equals("Notification box"));
		return f;
	}
}
