package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {
	
	private String name;
	
	private Collection<Message> messages;
	private Folder parent;
	private Collection<Folder> children;
	
	public Folder(){
		super();
	}
	
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@OneToMany(mappedBy = "folder")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	@Valid
	@ManyToOne
	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Folder> getChildren() {
		return children;
	}

	public void setChildren(Collection<Folder> children) {
		this.children = children;
	}
	
	

}
