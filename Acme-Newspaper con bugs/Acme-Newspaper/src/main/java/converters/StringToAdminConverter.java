
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdminRepository;
import domain.Admin;

@Component
@Transactional
public class StringToAdminConverter implements Converter<String, Admin> {

	@Autowired
	AdminRepository	adminRepository;


	@Override
	public Admin convert(final String arg0) {
		Admin res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.adminRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
