
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.SubscriptionRepository;
import domain.Subscription;

public class StringToSubscriptionConverter implements Converter<String, Subscription> {

	@Autowired
	private SubscriptionRepository	subscriptionRepository;


	@Override
	public Subscription convert(final String source) {
		final Subscription s;
		int id;
		try {
			id = Integer.valueOf(source);
			s = this.subscriptionRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return s;
	}

}
