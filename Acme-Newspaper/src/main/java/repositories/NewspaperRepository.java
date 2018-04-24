
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	//Periodicos del usuario 
	@Query("select n from Newspaper n where n.creator.userAccount.id = ?1")
	Collection<Newspaper> findNewspaperCreatedByUserAccountId(int id);
	//Periodicos publicados
	@Query("select n from Newspaper n where n.publicationDate != null")
	Collection<Newspaper> findPublicatedNewspaper();
	//Periodicos no publicados
	@Query("select n from Newspaper n where n.publicationDate = null")
	Collection<Newspaper> findNotPublicatedNewspaper();
	//Periodicos sospechosos
	@Query("select n from Newspaper n where n.marked = true")
	Collection<Newspaper> findMarkedNewspaper();
	//Busqueda por keyword
	@Query("select n from Newspaper n where (n.title like concat('%',?1,'%')or n.description like concat('%',?1,'%')) and n.publicationDate != null")
	Collection<Newspaper> findNewspapersByKeyword(String keyword);
	/////////////ACME 2.0
	//Periódicos de un volume
	//@Query("select n from Newspaper n join n.volumes v where v.id = ?1 and n.publicationDate != null")
	//Collection<Newspaper> findNewspapersByVolumeId(int volumeId);
}
