package muhasebe.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CustomMapper {

	@Autowired
	private ModelMapper mapper;

	/* Tekil sınıflar için mapper kullanımı */
	public <D, T> D map(final T source, Class<D> out) {
		configMapper();
		return mapper.map(source, out);
	}

	/* Liste için mapper kullanımı */
	public <D, T> List<D> mapAll(final Collection<T> source, Class<D> out) {
		configMapper();
		return source.stream().map(m -> map(m, out)).collect(Collectors.toList());
	}

	/** Page List için iterable gerekiyor */
	public <S, T> List<T> mapAll(Iterable<S> sourceIterable, Class<T> outCLass) {
		return StreamSupport.stream(sourceIterable.spliterator(), false).map(entity -> map(entity, outCLass))
				.collect(Collectors.toList());
	}

	/* Page için mapper kullanımı */
	public <D, T> Page<D> mapPage(Page<T> entities, Class<D> outCLass) {
		configMapper();
		return entities.map(entity -> mapper.map(entity, outCLass));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void configMapper() {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setFieldMatchingEnabled(true);
	}

}
