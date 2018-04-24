
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name = ?2")
	Folder findFolderByNameAndActor(Integer actorId, String s);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.parent = null")
	Collection<Folder> mainFolders(Integer actorId);
}
