package muhasebe.util.rsql.builder;

import muhasebe.util.rsql.argument.IArgumentParser;
import muhasebe.util.rsql.argument.IMapper;

public interface IBuilderTools {

	public IMapper getPropertiesMapper();

	public void setPropertiesMapper(IMapper mapper);

	public IArgumentParser getArgumentParser();

	public void setArgumentParser(IArgumentParser argumentParser);

	public IBuilderStrategy getPredicateBuilder();

	public void setPredicateBuilder(IBuilderStrategy predicateStrategy);

}
