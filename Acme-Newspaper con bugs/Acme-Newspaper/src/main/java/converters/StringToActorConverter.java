
package converters;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ActorRepository;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	ActorRepository	actorRepository;


	@Override
	public Actor convert(final String arg0) {
		Actor res = null;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				System.out.println(id);
				final Collection<Actor> actors = this.actorRepository.findAll();
				for (final Actor a : actors)
					if (a.getId() == id) {
						res = a;
						break;
					}
				System.out.println(res);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
