
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

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;
import forms.UserForm;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	public CustomerService() {
		super();
	}

	public Customer create() {
		final Customer res = new Customer();

		final UserAccount ua = this.userAccountService.create();
		res.setUserAccount(ua);

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);

		return res;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		if (customer.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			Assert.notNull(customer.getUserAccount().getPassword());
			final String hash = encoder.encodePassword(customer.getUserAccount().getPassword(), null);
			customer.getUserAccount().setPassword(hash);
		}

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		authorities.add(auth);
		customer.getUserAccount().setAuthorities(authorities);
		final UserAccount ua = this.userAccountService.save(customer.getUserAccount());
		customer.setUserAccount(ua);
		final Customer res = this.customerRepository.save(customer);
		Assert.notNull(res.getUserAccount().getUsername());
		Assert.notNull(res.getUserAccount().getPassword());
		return res;
	}

	public Customer findOne(final int idCustomer) {
		Assert.isTrue(idCustomer != 0);
		final Customer res = this.customerRepository.findOne(idCustomer);
		return res;
	}

	public Collection<Customer> findAll() {
		final Collection<Customer> res;
		res = this.customerRepository.findAll();
		return res;
	}

	public Customer findCustomerByUserAccountId(final int customerAccountId) {
		Assert.isTrue(customerAccountId != 0);
		Customer res;
		res = this.customerRepository.findCustomerByUserAccountId(customerAccountId);
		return res;
	}

	public Customer findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Customer res = this.findCustomerByUserAccountId(u.getId());
		return res;
	}

	public Customer reconstruct(final UserForm userForm, final BindingResult binding) {
		final Customer res = this.create();
		res.setName(userForm.getName());
		res.setSurname(userForm.getSurname());
		res.setAddress(userForm.getAddress());
		res.setPhone(userForm.getPhone());
		res.setEmail(userForm.getEmail());
		res.getUserAccount().setUsername(userForm.getUserAccount().getUsername());
		res.getUserAccount().setPassword(userForm.getUserAccount().getPassword());

		this.validator.validate(res, binding);
		return res;
	}
}
