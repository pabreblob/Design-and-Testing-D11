
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from Chirp c where c.marked = true")
	Collection<Chirp> findMarked();

	@Query("select c from Chirp c where c.creator.id =?1")
	Collection<Chirp> findByCreatorId(int creatorId);

}
