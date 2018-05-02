
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.User;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id = ?1")
	Admin findAdminByUserAccountId(int UserAccountId);

	//Dashboard================================================
	@Query("select distinct a.creator from Article a")
	Collection<User> listOfWriters();
	@Query("select distinct n.creator from Newspaper n")
	Collection<User> listOfUserWhoCreatedNewspaper();
	//	@Query("select distinct a.newspaper from Advertisement a")
	//	Collection<Newspaper> listOfAdvertisedNewspapers();
	@Query("select a from Article a where a.creator.id = ?1")
	Collection<Article> findArticlesPerUser(int userId);
	@Query("select (select count(n) from Newspaper n where n.free = 1)*1.0/count(ne) from Newspaper ne where ne.free = 0 ")
	Double ratioOfPublicNewspaperVersusPrivateNewspaper();
	@Query("select n from Newspaper n where n.free = 0")
	Collection<Newspaper> findPrivateNewspaper();
	@Query("select n from Newspaper n where n.free = 1")
	Collection<Newspaper> findPublicNewspaper();
	@Query("select a from Article a where a.newspaper.free = false")
	Collection<Article> findArticlesFromPrivateNewspaper();
	@Query("select a from Article a where a.newspaper.free = true")
	Collection<Article> findArticlesFromPublicNewspaper();
	@Query("select distinct s.customer from Subscription s")
	Collection<Customer> findCustomerWhoAreSubscribed();
	@Query("select (select count(n) from Newspaper n where n.free = 0 and n.creator.id = ?1)*1.0/count(ne) from Newspaper ne where ne.free = 1 and ne.creator.id = ?1")
	Double ratioOfPrivateNewspaperVersusPublicNewspaper(int userId);

	@Query("select distinct a.newspaper from Advertisement a")
	Collection<Newspaper> findAdvertisedNewspapers();
	@Query("select (select count(a) from Advertisement a where a.marked = 1)*1.0/count(ad) from Advertisement ad")
	Double ratioOfMarkedAdvertisments();

	@Query("select avg(v.newspapers.size) from Volume v")
	Double averageNewspapersPerVolume();
	@Query("select (select count(s) from Subscription s where s.volume = 1)*1.0/count(su) from Subscription su")
	Double ratioOfSubscriptionsToVolumesVersusNewspapers();

}
