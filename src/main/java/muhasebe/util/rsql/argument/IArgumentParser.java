package muhasebe.util.rsql.argument;

import java.util.List;

public interface IArgumentParser {
	/*
	 * query içinde gönderilen tek veya çoklu parametreleri pars edilecek metot
	 * imzalarını tutmaktadır. ArgumentFormatException ve IllegalArgumentException
	 * hata tiplerini içermektedir.
	 */

	<T> T parse(String argument, Class<T> type) throws AFormatException, IllegalArgumentException;

	<T> List<T> parse(List<String> arguments, Class<T> type) throws AFormatException, IllegalArgumentException;

}
