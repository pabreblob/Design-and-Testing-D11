package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Advertisement;

@Component
@Transactional
public class AdvertisementToStringConverter implements
		Converter<Advertisement, String> {
	
	public String convert(Advertisement advertisement){
		String res;
		if(advertisement == null)
			res = null;
		else
			res = String.valueOf(advertisement.getId());
		return res;
	}

}
