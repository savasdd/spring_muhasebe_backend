package muhasebe.util.rsql.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class JpaPredicateVisitor<T> extends AbstractJpaVisitor<Predicate, T>
		implements RSQLVisitor<Predicate, EntityManager> {

	/*
	 * bu sınıf abstract sınıfına ait tools metotunu parametre olarak predicate
	 * oluşturan sınıfa göndermektedir.
	 */

	private static final Logger LOG = Logger.getLogger(JpaPredicateVisitor.class.getName());
	private From root;

	public JpaPredicateVisitor(T... t) {
		super(t);
	}

	public JpaPredicateVisitor<T> defineRoot(From root) {
		this.root = root;
		return this;
	}

	public Predicate visit(AndNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating Predicate for AndNode: {0}", node);
		return PredicateBuilder.<T>createPredicate(node, root, entityClass, entityManager, getBuilderTools());
	}

	public Predicate visit(OrNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating Predicate for OrNode: {0}", node);
		return PredicateBuilder.<T>createPredicate(node, root, entityClass, entityManager, getBuilderTools());
	}

	public Predicate visit(ComparisonNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating Predicate for ComparisonNode: {0}", node);
		return PredicateBuilder.<T>createPredicate(node, root, entityClass, entityManager, getBuilderTools());
	}

}