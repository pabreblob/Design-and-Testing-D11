
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.VolumeRepository;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class VolumeService {

	@Autowired
	private VolumeRepository	volumeRepository;

	// Supporting services ----------------------------------------------------	
	@Autowired
	private UserService			userService;
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------
	public VolumeService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Volume createVolume() {
		final User creator = this.userService.findByPrincipal();
		final Volume res = new Volume();
		res.setCreator(creator);
		final List<Newspaper> newspapers = new ArrayList<Newspaper>();
		final List<Customer> customers = new ArrayList<Customer>();
		res.setNewspapers(newspapers);
		res.setCustomers(customers);
		return res;
	}
	public Volume save(final Volume volume) {
		assert volume != null;

		Volume res;
		final User user = this.userService.findByPrincipal();
		Assert.isTrue(volume.getCreator().equals(user));
		res=this.volumeRepository.save(volume);
		return res;
	}
	
	public Collection<Volume> findAll() {
		Collection<Volume> res;
		Assert.notNull(this.volumeRepository);
		res = this.volumeRepository.findAll();

		return res;
	}

	public Volume findOne(final int volumeId) {
		Assert.isTrue(volumeId != 0);
		Volume res;
		res = this.volumeRepository.findOne(volumeId);

		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<Volume> findVolumesByCreator(final int userId) {
		final Collection<Volume> res = this.volumeRepository.findVolumesByCreator(userId);
		return res;
	}
	public void addNewspaper(Volume volume, Newspaper newspaper){
		assert volume!=null;
		assert newspaper!=null;
		User user=this.userService.findByPrincipal();
		Assert.isTrue(user.getId()==volume.getCreator().getId());
		Assert.isTrue(!volume.getNewspapers().contains(newspaper));
		Assert.isTrue(user.getId()==newspaper.getCreator().getId());
		volume.getNewspapers().add(newspaper);
		newspaper.getVolumes().add(volume);
	}
	public void removeNewspaper(Volume volume, Newspaper newspaper){
		assert volume!=null;
		assert newspaper!=null;
		User user=this.userService.findByPrincipal();
		Assert.isTrue(user.getId()==volume.getCreator().getId());
		Assert.isTrue(volume.getNewspapers().contains(newspaper));
		Assert.isTrue(user.getId()==newspaper.getCreator().getId());
		volume.getNewspapers().remove(newspaper);
		newspaper.getVolumes().remove(volume);
	}
	public Volume reconstruct(final Volume volume, final BindingResult binding) {
		Volume result;
			result = volume;
			result.setCreator(this.userService.findByPrincipal());
			final List<Newspaper> newspapers = new ArrayList<Newspaper>();
			final List<Customer> customers = new ArrayList<Customer>();
			result.setNewspapers(newspapers);
			result.setCustomers(customers);
		this.validator.validate(result, binding);
		return result;
	}
	
	
}
