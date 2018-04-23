
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Subscription;

@Component
@Transactional
public class SubscriptionToStringConverter implements Converter<Subscription, String> {

	@Override
	public String convert(final Subscription s) {
		String res;
		if (s == null)
			res = null;
		else
			res = String.valueOf(s.getId());
		return res;
	}

}
