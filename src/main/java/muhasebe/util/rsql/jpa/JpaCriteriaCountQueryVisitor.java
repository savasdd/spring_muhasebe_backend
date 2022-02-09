package muhasebe.util.rsql.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class JpaCriteriaCountQueryVisitor<T> extends AbstractJpaVisitor<CriteriaQuery<Long>, T>
		implements RSQLVisitor<CriteriaQuery<Long>, EntityManager> {

	/*
	 * jpa visitor metotlarının EntityManager createBuilder özellikleri kullanılarak
	 * doldurulduğu sınıftır.And ,Or ve karşılaştırma operatorlarına göre query
	 * oluşturulup where koşulu ekleniyor.
	 */

	private static final Logger LOG = Logger.getLogger(JpaCriteriaCountQueryVisitor.class.getName());

	private final JpaPredicateVisitor<T> predicateVisitor;

	private Root<T> root;

	@SafeVarargs
	public JpaCriteriaCountQueryVisitor(T... t) {
		super(t);
		this.predicateVisitor = new JpaPredicateVisitor<T>(t);
	}

	protected JpaPredicateVisitor<T> getPredicateVisitor() {
		this.predicateVisitor.setBuilderTools(this.getBuilderTools());
		return this.predicateVisitor;
	}

	// And için query
	public CriteriaQuery<Long> visit(AndNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for AndNode: {0}", node);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		root = cq.from(entityClass);
		cq.select(cb.countDistinct(root));
		cq.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));

		return cq;
	}

	// Or için query
	public CriteriaQuery<Long> visit(OrNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for OrNode: {0}", node);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		root = cq.from(entityClass);
		cq.select(cb.countDistinct(root));
		root = cq.from(entityClass);
		cq.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));
		return cq;
	}

	// Karşılaştırma için query
	public CriteriaQuery<Long> visit(ComparisonNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for ComparisonNode: {0}", node);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		root = cq.from(entityClass);
		cq.select(cb.countDistinct(root));
		cq.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));
		return cq;
	}

	public Root<T> getRoot() {
		return root;
	}

	public void setRoot(Root<T> root) {
		this.root = root;
	}

}