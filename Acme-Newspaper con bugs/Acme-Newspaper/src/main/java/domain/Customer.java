
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	
	private Collection<Volume> volumes;

	public Customer() {
		super();
	}

	@NotNull
	@ManyToMany
	public Collection<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(Collection<Volume> volumes) {
		this.volumes = volumes;
	}
	
	

}
