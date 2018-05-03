
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Folder;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository		userRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private Validator			validator;
	@Autowired
	private FolderService		folderService;


	public UserService() {
		super();
	}

	public User create() {
		final User user = new User();

		final Collection<User> followers = new ArrayList<>();
		final Collection<User> following = new ArrayList<>();

		user.setFollowers(followers);
		user.setFollowing(following);

		final UserAccount ua = this.userAccountService.create();
		user.setUserAccount(ua);

		user.setFolders(new ArrayList<Folder>());
		user.getFolders().add(this.folderService.create());
		user.getFolders().add(this.folderService.create());
		user.getFolders().add(this.folderService.create());
		user.getFolders().add(this.folderService.create());
		user.getFolders().add(this.folderService.create());

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.USER);
		authorities.add(auth);
		user.getUserAccount().setAuthorities(authorities);

		return user;
	}
	public User save(final User user) {
		Assert.notNull(user);
		Assert.isTrue(!user.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!user.getUserAccount().getPassword().isEmpty());
		Assert.isTrue(!user.getEmail().isEmpty());
		Assert.isTrue(!user.getName().isEmpty());
		Assert.isTrue(!user.getSurname().isEmpty());

		if (user.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(user.getUserAccount().getPassword(), null);
			user.getUserAccount().setPassword(hash);
		}

		if (user.getId() == 0) {
			user.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			user.getFolders().addAll(folders);
		}

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.USER);
		authorities.add(auth);
		user.getUserAccount().setAuthorities(authorities);
		final UserAccount ua = this.userAccountService.save(user.getUserAccount());
		user.setUserAccount(ua);

		final User res = this.userRepository.save(user);
		return res;
	}

	public User findOne(final int idUser) {
		Assert.isTrue(idUser != 0);
		final User res = this.userRepository.findOne(idUser);
		return res;
	}

	public Collection<User> findAll() {
		final Collection<User> res;
		res = this.userRepository.findAll();
		return res;
	}

	public User findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final User res = this.userRepository.findUserByUserAccountId(userAccountId);
		return res;
	}

	public User findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final User res = this.userRepository.findUserByUserAccountId(u.getId());
		return res;
	}

	public void follow(final User follower, final User followed) {
		Assert.notNull(follower);
		Assert.notNull(followed);
		Assert.isTrue(!follower.getFollowing().contains(followed));
		Assert.isTrue(!followed.getFollowers().contains(follower));
		Assert.isTrue(follower.getId() != followed.getId());
		Assert.isTrue(follower.getId() != 0);
		Assert.isTrue(followed.getId() != 0);

		follower.getFollowing().add(followed);
		followed.getFollowers().add(follower);
	}

	public void unfollow(final User follower, final User followed) {
		Assert.notNull(follower);
		Assert.notNull(followed);
		Assert.isTrue(follower.getFollowing().contains(followed));
		Assert.isTrue(followed.getFollowers().contains(follower));
		Assert.isTrue(follower.getId() != followed.getId());
		Assert.isTrue(follower.getId() != 0);
		Assert.isTrue(followed.getId() != 0);

		follower.getFollowing().remove(followed);
		followed.getFollowers().remove(follower);
	}

	public User reconstruct(final UserForm userForm, final BindingResult binding) {
		final User res = this.create();
		res.setName(userForm.getName());
		res.setSurname(userForm.getSurname());
		res.setAddress(userForm.getAddress());
		res.setPhone(userForm.getPhone());
		res.setEmail(userForm.getEmail());
		res.getUserAccount().setUsername(userForm.getUsername());
		res.getUserAccount().setPassword(userForm.getPassword());

		System.out.println("A" + userForm.getName());
		System.out.println(userForm.getSurname());
		System.out.println(userForm.getAddress());
		System.out.println(userForm.getPhone());
		System.out.println(userForm.getEmail());
		System.out.println(userForm.getUsername());
		System.out.println(userForm.getPassword());
		this.validator.validate(userForm, binding);
		return res;
	}

}
