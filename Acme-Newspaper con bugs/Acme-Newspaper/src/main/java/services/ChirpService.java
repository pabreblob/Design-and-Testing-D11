
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.TabooWord;
import domain.User;

@Service
@Transactional
public class ChirpService {

	@Autowired
	private ChirpRepository		chirpRepository;
	@Autowired
	private UserService			userService;
	@Autowired
	private AdminService		adminService;
	@Autowired
	private TabooWordService	tabooWordService;
	@Autowired
	private Validator			validator;


	public ChirpService() {
		super();
	}

	public Chirp create() {
		final User c = this.userService.findByPrincipal();
		Assert.notNull(c);

		final Chirp res = new Chirp();

		//final Date moment = new Date(System.currentTimeMillis() - 1000);

		//res.setMarked(false);
		//res.setCreator(c);
		//res.setMoment(moment);

		return res;
	}

	public Chirp save(final Chirp c) {
		Assert.notNull(c);
		Assert.isTrue(c.getId() == 0);
		final User p = this.userService.findByPrincipal();
		Assert.isTrue(c.getCreator().equals(p));

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		c.setMoment(moment);

		final Collection<TabooWord> tw = this.tabooWordService.findAll();
		boolean taboow = false;
		for (final TabooWord word : tw) {
			//c.setMarked(c.getDescription().toLowerCase().matches(".*\\b" + t.getWord() + "\\b.*") || c.getTitle().toLowerCase().matches(".*\\b" + t.getWord() + "\\b.*"));
			taboow = c.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= c.getDescription().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		if (taboow)
			c.setMarked(true);
		final Chirp res = this.chirpRepository.save(c);

		return res;

	}

	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId > 0);
		return this.chirpRepository.findOne(chirpId);
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public void delete(final Chirp c) {
		Assert.notNull(this.adminService.findByPrincipal());
		Assert.notNull(c);

		this.chirpRepository.delete(c);
	}

	//Other

	public Collection<Chirp> findByCreatorId(final int creatorId) {
		return this.chirpRepository.findByCreatorId(creatorId);
	}

	public Collection<Chirp> findMarked() {
		return this.chirpRepository.findMarked();
	}

	public Chirp reconstruct(final Chirp c, final BindingResult binding) {
		final Chirp res = c;
		final User creator = this.userService.findByPrincipal();
		res.setCreator(creator);
		res.setMarked(false);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(res, binding);
		return res;
	}

}
