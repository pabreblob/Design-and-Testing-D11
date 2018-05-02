
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.TabooWord;

public interface TabooWordRepository extends JpaRepository<TabooWord, Integer> {

}
