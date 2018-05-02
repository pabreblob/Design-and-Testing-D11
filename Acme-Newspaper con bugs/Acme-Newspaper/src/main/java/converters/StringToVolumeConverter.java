
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import repositories.VolumeRepository;
import domain.Volume;

@Component
@Transactional
public class StringToVolumeConverter implements Converter<String, Volume> {

	@Autowired
	VolumeRepository	volumeRepository;


	@Override
	public Volume convert(final String arg0) {
		Volume res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.volumeRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
