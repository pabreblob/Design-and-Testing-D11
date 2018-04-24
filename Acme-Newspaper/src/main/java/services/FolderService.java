
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
			res = this.folderRepository.findOne(folder.getId());
			res.setName(folder.getName());
		}
		this.validator.validate(res, br);
		return res;
	}
	public Collection<Folder> findFolderByPrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return a.getFolders();
	}
	public Folder findFolderByNameAndActor(final String s) {
		return this.folderRepository.findFolderByNameAndActor(this.actorService.findByPrincipal().getId(), s);
	}

}
