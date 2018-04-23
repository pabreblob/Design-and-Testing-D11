
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	@Autowired
	private AdminService	adminService;


	/**
	 * Tests the method related to the dashboard information an administrator must have access to.
	 * <p>
	 * This method tests that only an administrator can access to dashboard related services. An actor not authenticated at admin will not be able to use these services.
	 * <p>
	 * Case 1: An actor logged in as an administrator tries to display the dashboard information. The information is displayed succesfully <br>
	 * Case 2: An actor who is not an administrator tries to display the dashboard information. This is expected to fail.<br>
	 * Case 3: A person who is not logged tries to display the dashboard
	 */
	@Test
	public void driverDashboard() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"user1", IllegalAccessException.class
			}, {
				null, IllegalAccessException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDashboard((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void templateDashboard(final String username, final Class<?> expected) {
		Class<?> caught = null;
		this.authenticate(username);
		try {
			//=========================
			//These lines will replace the access control given by the file security.xml
			//=========================
			try {
				final Admin a = this.adminService.findByPrincipal();
				Assert.notNull(a);
			} catch (final Throwable oops) {
				throw new IllegalAccessException();
			}
			//==========================
			this.adminService.averageNewspaperPerUser();
			this.adminService.standardDeviationNewspaperPerUser();
			this.adminService.averageArticlesPerWriter();
			this.adminService.standardDeviationArticlesPerWriter();
			this.adminService.averageArticlesPerNewspaper();
			this.adminService.standardDeviationArticlesPerNewspaper();
			this.adminService.newspaperWithTenPercent();
			this.adminService.ratioOfUserWhoHasCreatedANewspaper();
			this.adminService.ratioOfUserWhoHasWrittenAnArticle();
			this.adminService.averageFollowupPerArticle();
			this.adminService.averageFollowupPerArticleUpToWeek();
			this.adminService.ratioUserWhoPostAbove75OfAverage();
			this.adminService.ratioOfPublicNewspaperVersusPrivateNewspaper();
			this.adminService.averageNumberOfArticlesPerPrivateNewspaper();
			this.adminService.averageNumberOfArticlesPerPublicNewspaper();
			this.adminService.ratioOfSubscribersPerPrivateNewspaperVersusAllCustomers();
			this.adminService.averageRatioOfPrivateVersusPublicNewspapersPerPublisher();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

}
