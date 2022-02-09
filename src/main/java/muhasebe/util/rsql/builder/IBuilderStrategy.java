package muhasebe.util.rsql.builder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import cz.jirutka.rsql.parser.ast.Node;

public interface IBuilderStrategy {
	/*
	 * jpa metotlarını barındıran interface yapısıdır.Rsql'e ait Node ve jpanın
	 * from, predicate ve manager parametre olarak kullanmaktadır.
	 */

	public <T> Predicate createPredicate(Node node, From root, Class<T> entity, EntityManager manager,
			IBuilderTools tools) throws IllegalArgumentException;

}
