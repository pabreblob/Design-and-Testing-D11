
package repositories;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {
	@Query("select v from Volume v where v.creator.id = ?1")
	Collection<Volume> findVolumesByCreator(int userId);

	

}
