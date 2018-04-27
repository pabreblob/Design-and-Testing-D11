
package services;

import java.util.ArrayList;
import java.util.Collection;

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

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository	folderRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	//@Autowired
	//private MessageService		messageService;
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

		//if (!f.getMessages().isEmpty())
		//	for (final Message message : f.getMessages())
		//		this.messageService.deleteFromFolder(message, f);

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
		Assert.isTrue(f.getName().replace(" ", "").length() != 0);

		return this.folderRepository.save(f);
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
}
