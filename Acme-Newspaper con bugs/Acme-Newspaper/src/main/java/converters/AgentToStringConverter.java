package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Agent;

@Component
@Transactional
public class AgentToStringConverter implements Converter<Agent, String>{
	
	public String convert(Agent agent){
		String res;
		if(agent == null)
			res = null;
		else
			res = String.valueOf(agent.getId());
		return res;
	}

}
