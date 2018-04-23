
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TabooWordRepository;
import domain.TabooWord;

@Component
@Transactional
public class StringToTabooWordConverter implements Converter<String, TabooWord> {

	@Autowired
	TabooWordRepository	tabooWordRepository;


	@Override
	public TabooWord convert(final String arg0) {
		TabooWord res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.tabooWordRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
