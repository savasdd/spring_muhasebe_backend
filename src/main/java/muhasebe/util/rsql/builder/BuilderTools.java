package muhasebe.util.rsql.builder;

import muhasebe.util.rsql.argument.ArgumentParser;
import muhasebe.util.rsql.argument.CMapper;
import muhasebe.util.rsql.argument.IArgumentParser;
import muhasebe.util.rsql.argument.IMapper;

public class BuilderTools implements IBuilderTools {

	/*
	 * builder tools üzerinden interface yapıları sınıf yapılarına atanmaktadır.
	 * IMapper ve IArgumentParser get metotları içinde bu şekilde çalışmaktadır.
	 */

	private IMapper mapper;
	private IArgumentParser argumentParser;
	private IBuilderStrategy delegate;

	@Override
	public IMapper getPropertiesMapper() {
		if (this.mapper == null) {
			this.mapper = new CMapper();
		}
		return this.mapper;
	}

	@Override
	public void setPropertiesMapper(IMapper mapper) {
		this.mapper = mapper;

	}

	@Override
	public IArgumentParser getArgumentParser() {
		if (this.argumentParser == null) {
			this.argumentParser = new ArgumentParser();
		}
		return this.argumentParser;
	}

	@Override
	public void setArgumentParser(IArgumentParser argumentParser) {
		this.argumentParser = argumentParser;

	}

	@Override
	public IBuilderStrategy getPredicateBuilder() {
		return this.delegate;
	}

	@Override
	public void setPredicateBuilder(IBuilderStrategy predicateStrategy) {
		this.delegate = predicateStrategy;

	}

}
