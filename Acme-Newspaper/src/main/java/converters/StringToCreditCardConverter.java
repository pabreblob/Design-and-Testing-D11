
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CreditCard;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String arg0) {
		final CreditCard res;
		final String parts[];

		if (arg0 == null)
			res = null;
		else
			try {
				parts = arg0.split("\\|");
				res = new CreditCard();
				res.setHolderName(URLDecoder.decode(parts[0], "UTF-8"));
				res.setBrandName(URLDecoder.decode(parts[1], "UTF-8"));
				res.setNumber(URLDecoder.decode(parts[2], "UTF-8"));
				res.setExpMonth(Integer.valueOf(URLDecoder.decode(parts[3], "UTF-8")));
				res.setExpYear(Integer.valueOf(URLDecoder.decode(parts[4], "UTF-8")));
				res.setCvv(Integer.valueOf(URLDecoder.decode(parts[5], "UTF-8")));
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		return res;
	}
}
