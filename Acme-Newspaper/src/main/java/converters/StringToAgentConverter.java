package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AgentRepository;

import domain.Agent;

@Component
@Transactional
public class StringToAgentConverter implements Converter<String, Agent> {
	
	@Autowired
	AgentRepository agentRepository;
	
	public Agent convert(String arg0){
		Agent res;
		int id;
		
		try{
			if(StringUtils.isEmpty(arg0))
				res = null;
			else{
				id = Integer.valueOf(arg0);
				res = this.agentRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
