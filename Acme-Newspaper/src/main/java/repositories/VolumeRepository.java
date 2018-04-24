
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {

	

}
