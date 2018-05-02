
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.TabooWord;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private TabooWordService	tabooWordService;

	@Autowired
	private Validator			validator;


	public Message create() {
		Message res;
		res = new Message();
		final Actor sender = this.actorService.findByPrincipal();
		res.setSender(sender);
		final Collection<Actor> recipients = new ArrayList<Actor>();
		res.setRecipients(recipients);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setFolder(this.folderService.findFolderByNameAndActor("Out box"));
		return res;
	}

	public Message findOne(final int messageId) {
		Message res;
		res = this.messageRepository.findOne(messageId);
		return res;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final String body = message.getBody().toLowerCase();
		final String subject = message.getSubject().toLowerCase();
		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			taboow = body.matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= subject.matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		final Actor sender = this.actorService.findByPrincipal();
		Assert.isTrue(sender.equals(message.getSender()));
		Assert.isTrue(sender.getFolders().contains(message.getFolder()));
		Assert.isTrue(message.getFolder().getName().equals("Out box"));
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		Message res;
		res = this.messageRepository.save(message);
		final Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(new ArrayList<Actor>(message.getRecipients()).get(0));
		res.getFolder().getMessages().add(res);
		for (final Actor a : recipients)
			for (final Folder f : a.getFolders())
				if (!taboow && f.getName().equals("In box")) {
					message.setFolder(f);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				} else if (taboow && f.getName().equals("Spam box")) {
					message.setFolder(f);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				}
		return res;
	}
	public Message broadcast(final Message message) {
		Assert.notNull(message);
		final Actor sender = this.actorService.findByPrincipal();
		message.setSender(sender);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		Message res;
		res = this.messageRepository.save(message);
		final Collection<Actor> recipients = res.getRecipients();
		Assert.isTrue(sender.equals(message.getSender()));
		Assert.isTrue(sender.getFolders().contains(message.getFolder()));
		Assert.isTrue(message.getFolder().getName().equals("Out box"));
		res.getFolder().getMessages().add(res);
		for (final Actor a : recipients)
			for (final Folder f : a.getFolders())
				if (f.getName().equals("Notification box")) {
					message.setFolder(f);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				}
		return res;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		final Folder folder = message.getFolder();
		Assert.notNull(folder);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(folder.getId() != 0);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(folder));
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(folder.getMessages().contains(message));
		if (folder.getName().equals("Trash box")) {
			folder.getMessages().remove(message);
			this.messageRepository.delete(message.getId());
		} else {
			final Folder trashf = this.folderService.findFolderByNameAndActor("Trash box");
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(trashf));
			folder.getMessages().remove(message);
			message.setFolder(trashf);
			trashf.getMessages().add(message);
		}
	}

	public void moveToFolder(final Message message, final Folder target) {
		Assert.notNull(message);
		final Folder source = message.getFolder();
		Assert.notNull(source);
		Assert.notNull(target);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(source.getId() != 0);
		Assert.isTrue(target.getId() != 0);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(source));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(target));
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(source.getMessages().contains(message));
		source.getMessages().remove(message);
		message.setFolder(target);
		target.getMessages().add(message);
	}

	public Collection<Message> findMessagesByFolderId(final int folderId) {
		Assert.isTrue(folderId != 0);
		final Collection<Message> res = this.messageRepository.findMessagesByFolderId(folderId);
		return res;
	}

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;

		result = message;
		result.setSender(this.actorService.findByPrincipal());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setFolder(this.folderService.findFolderByNameAndActor("Out box"));
		if (message.getRecipients() == null)
			result.setRecipients(new ArrayList<Actor>());

		this.validator.validate(result, binding);
		return result;
	}

	public Message reconstructAdmin(final Message message, final BindingResult binding) {
		Message result;

		result = message;
		result.setSender(this.actorService.findByPrincipal());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setFolder(this.folderService.findFolderByNameAndActor("Out box"));
		result.setRecipients(this.actorService.findAll());

		this.validator.validate(result, binding);
		return result;
	}
}
