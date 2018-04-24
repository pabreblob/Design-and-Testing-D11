package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdvertisementRepository;

import domain.Advertisement;

@Component
@Transactional
public class StringToAdvertisementConverter implements
		Converter<String, Advertisement> {
	
	 @Autowired
	 AdvertisementRepository advertisementRepository;
	 
	 public Advertisement convert(String arg0){
		 Advertisement res;
		 int id;
		 
		 try{
			 if(StringUtils.isEmpty(arg0))
				 res = null;
			 else{
				 id = Integer.valueOf(arg0);
				 res = this.advertisementRepository.findOne(id);
			 }
		 }catch(Throwable oops){
			 throw new IllegalArgumentException(oops);
		 }
		 return res;
	 }

}
