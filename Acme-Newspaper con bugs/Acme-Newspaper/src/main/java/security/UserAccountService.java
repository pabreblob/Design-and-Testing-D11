
package security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Simple CRUD methods ----------------------------------------------------
	public UserAccount create() {
		final UserAccount res = new UserAccount();
		res.setAuthorities(new ArrayList<Authority>());
		return res;
	}

	public UserAccount save(final UserAccount uc) {
		assert uc != null;
		assert uc.getUsername() != null;
		assert uc.getPassword() != null;
		assert uc.getAuthorities().size() > 0;
		return this.userAccountRepository.save(uc);
	}

	// Other business methods -------------------------------------------------

}
