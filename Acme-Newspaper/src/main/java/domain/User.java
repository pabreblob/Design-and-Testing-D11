
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private Collection<User>	followers;
	private Collection<User>	following;


	public User() {
		super();
	}

	@NotNull
	@ManyToMany(mappedBy = "following")
	public Collection<User> getFollowers() {
		return this.followers;
	}

	public void setFollowers(final Collection<User> followers) {
		this.followers = followers;
	}

	@NotNull
	@ManyToMany
	public Collection<User> getFollowing() {
		return this.following;
	}

	public void setFollowing(final Collection<User> following) {
		this.following = following;
	}

}
