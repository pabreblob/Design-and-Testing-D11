
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "name"), @Index(columnList = "parent_id")
})
public class Folder extends DomainEntity {

	private String				name;

	private Collection<Message>	messages;
	private Folder				parent;
	private Collection<Folder>	children;


	public Folder() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@OneToMany(mappedBy = "folder")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@Valid
	@ManyToOne
	public Folder getParent() {
		return this.parent;
	}

	public void setParent(final Folder parent) {
		this.parent = parent;
	}

	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Folder> getChildren() {
		return this.children;
	}

	public void setChildren(final Collection<Folder> children) {
		this.children = children;
	}

}
