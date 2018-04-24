package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
	
	@Query("select a from Advertisement a where a.owner.id = ?1")
	Collection<Advertisement> findAdvertisementByAgentId(int agentId);
	
	@Query("select a from Advertisement a where a.marked = true")
	Collection<Advertisement> findMarked();

}
