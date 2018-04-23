
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.CustomerRepository;
import domain.Customer;

public class StringToCustomerConverter implements Converter<String, Customer> {

	@Autowired
	private CustomerRepository	customerRepository;


	@Override
	public Customer convert(final String arg0) {
		Customer res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.customerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
