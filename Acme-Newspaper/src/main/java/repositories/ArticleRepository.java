
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where a.newspaper.id = ?1")
	Collection<Article> findArticlesByNewspaper(int newspaperId);
	@Query("select a from Article a where a.creator.id = ?1 and a.newspaper.publicationDate!=null")
	Collection<Article> findPublishedArticlesByUser(int userId);
	@Query("select a from Article a where a.marked =true")
	Collection<Article> findMarkedArticles();
	@Query("select a from Article a where a.creator.id = ?1 and a.finalMode=false")
	Collection<Article> findEditableArticlesByUser(int userId);
	@Query("select a from Article a where (a.title like concat('%',?1,'%')or a.summary like concat('%',?1,'%')or a.body like concat('%',?1,'%')) and a.moment != null")
	Collection<Article> findArticlesByKeyword(String keyword);

}
