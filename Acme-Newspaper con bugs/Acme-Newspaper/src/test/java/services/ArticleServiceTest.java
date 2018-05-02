
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	@Autowired
	private ArticleService		articleService;
	@Autowired
	private NewspaperService	newspaperService;


	/**
	 * Tests the searh of articles by keyword.
	 * <p>
	 * This method is used to test the searh of articles by keyword. This method is always expected to work, in order to test negative use cases we will pass a keyword a word that is not in any article.<br>
	 * The list of articles will be retrieved, and if it's empty, the test will fail.
	 * <p>
	 * Functional requirement :
	 * <p>
	 * 4. An actor who is not authenticated must be able to:
	 * <p>
	 * 4. Search for a published article using a single key word that must appear somewhere in its title, summary, or body.
	 * <p>
	 * Case 1: Searchs for an article that contains the word "article".A list of articles will be retrieved. <br>
	 * Case 2: Searchs for an article that contains the word "fail".No article contains this word.An empty list will be retrieved.<br>
	 * Case 3: Searchs for an article that contains the word "fail2".No article contains this word.An empty list will be retrieved.
	 */
	@Test
	public void driverSearchArticle() {
		final Object testingData[][] = {
			{
				"article", null
			}, {
				"fail", IllegalArgumentException.class
			}, {
				"fail2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSearch((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing searching an article by keyword.
	 * <p>
	 * This method defines the template used for the tests the search of an article by keyword.
	 * 
	 * @param keyword
	 *            The keyword that must be contained in the article . In the first test case, there are articles that contain it,
	 *            and they will be listed. In the second and third test cases, there are no articles that matches this search criteria and an empty list will be retrieved.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSearch(final String keyword, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final List<Article> articles = new ArrayList<Article>(this.articleService.findArticlesByKeyword(keyword));
			Assert.isTrue(articles.size() > 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation and later edition of an article attached to a newspaper.
	 * <p>
	 * This method tests the creation of an article attached to a newspaper that is not published yet. Functional requirement:
	 * <p>
	 * 6. An actor who is authenticated as a user must be able to:
	 * <p>
	 * Write an article and attach it to any newspaper that has not been published, yet.Note that articles may be saved in draft mode, which allows to modify them later, or<br>
	 * final model, which freezes them forever.
	 * <p>
	 * Case 1: An user creates an article in draft mode associated with a newspaper.Afterwards, he edits the article's title. The process is done succesfully. <br>
	 * Case 2: An user creates an article in final mode associated with a newspaper.Afterwards, he tries to edit the article's title. The process is expected to fail.<br>
	 * Case 3: An user creates an article in draft mode associated with a newspaper.Afterwards, a different user tries to edit the article's title. The process is expected to fail.
	 */
	@Test
	public void driverSaveArticle() {
		final Object testingData[][] = {
			{
				"user1", "Newspaper1", false, "User1", null
			}, {
				"user1", "Newspaper1", true, "User1", IllegalArgumentException.class
			}, {
				"user1", "Newspaper1", false, "User2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (boolean) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	/**
	 * Template for testing the creation and edition of an article.
	 * <p>
	 * This method defines the template used for the tests that check the creation and edition of an article.
	 * 
	 * @param user1
	 *            The user that creates the first article. It must not be null.
	 * @param newspaperId
	 *            The newspaper the article is associated with. It must not be null and must not be published.
	 * @param finalMode
	 *            A boolean which determines wether or not the article can be modified. When its values is True, the article cannot be edited
	 * @param user2
	 *            The user that tries to edit the first article after it has been created. It must be the same as the one who created it.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSave(final String user1, final int newspaperId, final boolean finalMode, final String user2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user1);
			final Newspaper newspaper = this.newspaperService.findOne(newspaperId);
			final Article article = this.articleService.createArticle();
			article.setBody("body");
			article.setFinalMode(finalMode);
			article.setNewspaper(newspaper);
			article.setSummary("summary");
			article.setTitle("title1");
			final Article res = this.articleService.save(article);
			super.unauthenticate();
			super.authenticate(user2);
			res.setTitle("title2");
			Assert.isTrue(!res.isFinalMode());
			this.articleService.save(res);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	/**
	 * Tests the deletion of an article.
	 * <p>
	 * This method tests the listing of articles which contains a taboo word and the deletion of an article attached to a newspaper that is not published yet. Functional requirement:
	 * <p>
	 * 7. An actor who is authenticated as an administrator must be able to:
	 * <p>
	 * 1. Remove an article that he or she thinks is inappropriate.
	 * <p>
	 * 17. An actor who is authenticated as an administrator must be able to:
	 * <p>
	 * 2. List the articles that contain taboo words.
	 * <p>
	 * Case 1: An administrator lists the articles that contains a taboo word.Afterwards, he deletes the article. The process is done succesfully. <br>
	 * Case 2: An administrator lists the articles that contains a taboo word.Afterwards, he tries to delete an article that does not exist. The process is expected to fail.<br>
	 * Case 3: An user tries to list the articles that contains a taboo word.Afterwards, he tries to delete an article. The process is expected to fail.
	 */
	@Test
	public void driverDeleteArticle() {
		final Object testingData[][] = {
			{
				"admin", "Article1", null
			}, {
				"admin", "non-existent", NullPointerException.class
			}, {
				"user1", "Article1", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the deletion of an article.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of an article.
	 * 
	 * @param actor
	 *            The actor who deletes the article. It must be an administrator.
	 * @param article
	 *            The article that is being deleted. It must be an existent article.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final String article, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer articleId;
		try {
			super.authenticate(actor);
			this.articleService.findMarkedArticlesByUser();

			if (article.equals("non-existent"))
				articleId = null;
			else
				articleId = this.getEntityId(article);
			this.articleService.delete(this.articleService.findOne(articleId));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
