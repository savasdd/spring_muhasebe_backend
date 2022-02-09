package muhasebe.util.rsql.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class JpaCriteriaQueryVisitor<T> extends AbstractJpaVisitor<CriteriaQuery<T>, T>
		implements RSQLVisitor<CriteriaQuery<T>, EntityManager> {

	/*
	 * jpa visitor metotlarının EntityManager createBuilder özellikleri kullanılarak
	 * doldurulduğu sınıftır.Bu sınıfta ek olarak generic olarak alınan Modeller
	 * üzerinde query oluşturuluyor.And ,Or ve karşılaştırma operatorlarına göre
	 * query oluşturulup where koşulu ekleniyor. Pageable özelliği sort olup
	 * olmadığına göre query sorgusu değişiyor.
	 */

	private static final Logger LOG = Logger.getLogger(JpaCriteriaQueryVisitor.class.getName());

	private final JpaPredicateVisitor<T> predicateVisitor;
	private Pageable page;

	public JpaCriteriaQueryVisitor(Pageable page, T... t) {
		super(t);
		this.page = page;
		this.predicateVisitor = new JpaPredicateVisitor<T>(t);
	}

	protected JpaPredicateVisitor<T> getPredicateVisitor() {
		this.predicateVisitor.setBuilderTools(this.getBuilderTools());
		return this.predicateVisitor;
	}

	// And için query
	public CriteriaQuery<T> visit(AndNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for AndNode: {0}", node);
		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
		From root = criteria.from(entityClass);
		CriteriaQuery<T> query = null;
		if (page != null)
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager))
					.orderBy(QueryUtils.toOrders(page.getSort(), root, entityManager.getCriteriaBuilder()));
		else
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));

		return query;
	}

	// Or için query
	public CriteriaQuery<T> visit(OrNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for OrNode: {0}", node);
		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
		From root = criteria.from(entityClass);
		CriteriaQuery<T> query = null;
		if (page != null)
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager))
					.orderBy(QueryUtils.toOrders(page.getSort(), root, entityManager.getCriteriaBuilder()));
		else
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));

		return query;
	}

	// Karşılaştırma operatorlari için query
	public CriteriaQuery<T> visit(ComparisonNode node, EntityManager entityManager) {
		LOG.log(Level.INFO, "Creating CriteriaQuery for ComparisonNode: {0}", node);
		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(entityClass);
		From root = criteria.from(entityClass);
		CriteriaQuery<T> query = null;
		if (page != null)
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager))
					.orderBy(QueryUtils.toOrders(page.getSort(), root, entityManager.getCriteriaBuilder()));
		else
			query = criteria.where(this.getPredicateVisitor().defineRoot(root).visit(node, entityManager));

		return query;
	}

}