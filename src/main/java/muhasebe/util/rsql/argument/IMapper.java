package muhasebe.util.rsql.argument;

public interface IMapper {

	/* mapper işlemleri için kullanılacak metot */

	String translate(String selector, Class<?> entityClass);
}
