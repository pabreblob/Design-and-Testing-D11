
package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Newspaper;
import domain.Volume;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class VolumeServiceTest extends AbstractTest {

	@Autowired
	private VolumeService		volumeService;
	@Autowired
	private NewspaperService		newspaperService;


	

	/**
	 * Tests the creation of volumes and the addition of newspapers to volumes.
	 * <p>
	 * This method tests the creation of volumes and the addition of newspapers to volumes. Functional requirement:<br>
	 * 10. An actor who is authenticated as a user must be able to:<br>
	 * 1. Create a volume with as many published newspapers as he or she wishes. Note that the newspapers in a volume can<br>
	 *  be added or removed at any time. The same newspaper may be used to create different volumes.
	 * Case 1: An user creates a volume and then adds a newspaper he has created. When listing the newspapers of that volume, the newspaper the user has added must appear. The process is done succesfully. <br>
	 * Case 2: An user creates a volume and then adds a newspaper he has not created. When listing the newspapers of that volume, the newspaper the user has added must appear. The process is expected to fail. <br>
	 * Case 3: An user creates a volume and then adds a newspaper that does not exist. When listing the newspapers of that volume, the newspaper the user has added must appear. The process is expected to fail. <br>
	 */
	@Test
	public void driverSaveArticle() {
		final Object testingData[][] = {
			{
				"user1", "Testvolume1","Newspaper2", null
			}, {
				"user1", "Testvolume2","Newspaper7", IllegalArgumentException.class
			}, {
				"user1", "Testvolume3","non-valid", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0],(String) testingData[i][1], (String) testingData[i][2],(Class<?>) testingData[i][3]);
	}
	

	/**
	 * Template for testing the creation of volumes and the addition of newspapers to volumes.
	 * <p>
	 * This method defines the template used for the tests that check the creation of volumes and the addition of newspapers to volumes.
	 * 
	 * @param user1
	 *            The user that creates the volume. It must not be null.
	 * @param volumeTitle
	 *            The title of the volume that is being created. It must not be null.
	 * @param newspaperName
	 *            The name of the newspaper the user wants to add to the volume. It must not be null, it must be a newspaper the user has published and it must not have been already added to the volume.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateSave(final String user1, final String volumeTitle,  final String newspaperName, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer newspaperId;
		try {
			if (newspaperName.equals("non-valid"))
				newspaperId = null;
			else
				newspaperId = this.getEntityId(newspaperName);
			super.authenticate(user1);
			final Volume volume = this.volumeService.createVolume();
			volume.setTitle(volumeTitle);
			volume.setDescription("desc");
			volume.setYear("2018");
			volume.setPrice(20.0);
			this.volumeService.save(volume);
			this.volumeService.addNewspaper(volume,this.newspaperService.findOne(newspaperId) );
			Assert.isTrue(volume.getNewspapers().contains(this.newspaperService.findOne(newspaperId)));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	/**
	 * Tests the edition of a volume.
	 * <p>
	 * This method tests the edition of a volume. Functional requirement:<br>
	 * 10. An actor who is authenticated as a user must be able to:<br>
	 * 1. Create a volume with as many published newspapers as he or she wishes. Note that the newspapers in a volume can<br>
	 *  be added or removed at any time. The same newspaper may be used to create different volumes.
	 * Case 1: An user edits a volume he has created.The process is done succesfully. <br>
	 * Case 2: An user edits a volume he has not created. The process is expected to fail. <br>
	 * Case 3: An user edits a volume that does not exist. The process is expected to fail. <br>
	 */
	@Test
	public void driverEditArticle() {
		final Object testingData[][] = {
			{
				"user1", "Volume2",null
			}, {
				"user1", "Volume4", IllegalArgumentException.class
			}, {
				"user1", "non-valid", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],(String) testingData[i][1],(Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the creation of volumes and the addition of newspapers to volumes.
	 * <p>
	 * This method defines the template used for the tests that check the creation of volumes and the addition of newspapers to volumes.
	 * 
	 * @param user1
	 *            The user that edits the volume. It must not be null.
	 * @param volumeTitle
	 *            The title of the volume that is being edited.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateEdit(final String user1, final String volumeTitle, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer volumeId;
		try {
			if (volumeTitle.equals("non-valid"))
				volumeId = null;
			else
				volumeId = this.getEntityId(volumeTitle);
			super.authenticate(user1);
			final Volume volume = this.volumeService.findOne(volumeId);
			volume.setTitle("edited");
			this.volumeService.save(volume);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	/**
	 * Tests the removal of a newspaper from a volume.
	 * <p>
	 * This method tests the removal of a newspaper from a volume. Functional requirement:<br>
	 * 10. An actor who is authenticated as a user must be able to:<br>
	 * 1. Create a volume with as many published newspapers as he or she wishes. Note that the newspapers in a volume can<br>
	 *  be added or removed at any time. The same newspaper may be used to create different volumes.
	 * Case 1: An user removes a newspaper from a volume he has created.The process is done succesfully. <br>
	 * Case 2: An user removes a newspaper from a volume he has not created. The process is expected to fail. <br>
	 * Case 3: An user removes a newspaper from a volume that does not exist. The process is expected to fail. <br>
	 */
	@Test
	public void driverRemoveArticle() {
		final Object testingData[][] = {
			{
				"user1", "Volume2","Newspaper2",null
			}, {
				"user1", "Volume4","Newspaper7", IllegalArgumentException.class
			}, {
				"user1", "non-valid","Newspaper2", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRemove((String) testingData[i][0],(String) testingData[i][1],this.getEntityId((String) testingData[i][2]),(Class<?>) testingData[i][3]);
	}
	/**
	 * Template for testing the the removal of a newspaper from a volume.
	 * <p>
	 * This method defines the template used for the tests that check the removal of a newspaper from a volume.
	 * 
	 * @param user1
	 *            The user that removes the volume. It must not be null.
	 * @param volumeTitle
	 *            The title of the volume that is having a newspaper removed.
	 * @param newspaperId
	 *            The Id of the volume that is being removed.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateRemove(final String user1, final String volumeTitle,final int newspaperId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Integer volumeId;
		Newspaper newspaper=this.newspaperService.findOne(newspaperId);
		try {
			if (volumeTitle.equals("non-valid"))
				volumeId = null;
			else
				volumeId = this.getEntityId(volumeTitle);
			super.authenticate(user1);
			final Volume volume = this.volumeService.findOne(volumeId);
			this.volumeService.removeNewspaper(volume, newspaper);
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
}

