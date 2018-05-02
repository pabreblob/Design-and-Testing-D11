
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Article;
import domain.FollowUp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FollowUpServiceTest extends AbstractTest {

	@Autowired
	private ArticleService	articleService;
	@Autowired
	private FollowUpService	followUpService;


	/**
	 * Tests the creation of a followup related to an article.
	 * <p>
	 * This method tests the creation of a followup related to an article. Information requirement:
	 * <p>
	 * 14. The writer of an article may write follow-ups on it. Follow-ups can be written only after an<br>
	 * article is saved in final mode and the corresponding newspaper is published. For every follow-<br>
	 * up, the system must store the following data: title, publication moment, summary, text,<br>
	 * and optional pictures.
	 * <p>
	 * Case 1: An user creates a followup associated with an article he has written in a published newspaper.The process is done succesfully. <br>
	 * Case 2: An user tries to create a followup associated with an article he has written in a newspaper which has not been published. The process is expected to fail.<br>
	 * Case 3: An user tries to create a followup associated with an article he has not written in a published newspaper. The process is expected to fail.
	 */
	@Test
	public void driverSaveFollowUp() {
		final Object testingData[][] = {
			{
				"user1", "Article2", null
			}, {
				"user3", "Article13", IllegalArgumentException.class
			}, {
				"user3", "Article2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation and edition of followUp.
	 * <p>
	 * This method defines the template used for the tests that check the creation and edition of a followUp.
	 * 
	 * @param user
	 *            The user that creates the followUp. It must be the author of the article the followUp is associated to.
	 * @param articleId
	 *            The article the followUp is associated with. It must be in final mode and its newspaper must be published.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSave(final String user, final int articleId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			final Article article = this.articleService.findOne(articleId);
			final FollowUp followUp = this.followUpService.createFollowUp(article);
			followUp.setBody("body");
			followUp.setArticle(article);
			followUp.setSummary("summary");
			followUp.setTitle("title1");
			this.followUpService.save(followUp);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a followUp.
	 * <p>
	 * This method tests the listing of followUps which contains a taboo word and the deletion of a followUp attached to an article . Functional requirement(we consider the followups as articles):
	 * <p>
	 * 7. An actor who is authenticated as an administrator must be able to:
	 * <p>
	 * 1. Remove an article that he or she thinks is inappropriate.
	 * <p>
	 * 17. An actor who is authenticated as an administrator must be able to:
	 * <p>
	 * 2. List the articles that contain taboo words.
	 * <p>
	 * Case 1: An administrator lists the followUps that contains a taboo word.Afterwards, he deletes a followUp. The process is done successfully. <br>
	 * Case 2: An administrator lists the followUps that contains a taboo word.Afterwards, he tries to delete a followUp that does not exist. The process is expected to fail.<br>
	 * Case 3: An user tries to list the followUps that contains a taboo word.Afterwards, he tries to delete a followUp. The process is expected to fail.
	 */
	@Test
	public void driverDeleteArticle() {
		final Object testingData[][] = {
			{
				"admin", "FollowUp2", null
			}, {
				"admin", "non-existent", NullPointerException.class
			}, {
				"user1", "FollowUp2", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the deletion of a followUp.
	 * <p>
	 * This method defines the template used for the tests that check the the deletion of a followUp.
	 * 
	 * @param actor
	 *            The actor who deletes the followUp. It must be an administrator.
	 * @param article
	 *            The followUp that is being deleted. It must be an existent followUp.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final String followUp, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer followUpId;
		try {
			super.authenticate(actor);
			this.followUpService.findMarkedFollowUpsByUser();

			if (followUp.equals("non-existent"))
				followUpId = null;
			else
				followUpId = this.getEntityId(followUp);
			this.followUpService.delete(this.followUpService.findOne(followUpId));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
