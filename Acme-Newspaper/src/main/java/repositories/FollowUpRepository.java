
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FollowUp;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Integer> {

	@Query("select f from FollowUp f where f.article.id = ?1")
	Collection<FollowUp> findFollowUpsByArticle(int articleId);
	@Query("select f from FollowUp f where f.article.creator.id = ?1")
	Collection<FollowUp> findFollowUpsByUser(int userId);
	@Query("select f from FollowUp f where f.marked =true")
	Collection<FollowUp> findMarkedFollowUps();
}
