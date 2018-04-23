
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Component
@Transactional
public class StringToFollowUpConverter implements Converter<String, FollowUp> {

	@Autowired
	FollowUpRepository	followUpRepository;


	@Override
	public FollowUp convert(final String arg0) {
		FollowUp res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.followUpRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
