package muhasebe.util.rsql.jpa;

import javax.persistence.EntityManager;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import muhasebe.util.rsql.builder.BuilderTools;
import muhasebe.util.rsql.builder.IBuilderTools;

public abstract class AbstractJpaVisitor<T, E> implements RSQLVisitor<T, EntityManager> {

	/*
	 * bu abstract sınıfı üzerinden rsql visitor metotlarını kullanacağız. sınıf
	 * içinde builder interface olduğu için mapper, parser ve predicate metotlarına
	 * erişeceğiz. ek olarak rsqlvisitor interface visit metotlarına erişeceğiz.
	 */

	protected Class<E> entityClass;

	protected IBuilderTools builderTools;

	public AbstractJpaVisitor(E... e) {
		// getting class from template... :P
		if (e.length == 0) {
			entityClass = (Class<E>) e.getClass().getComponentType();
		} else {
			entityClass = (Class<E>) e[0].getClass();
		}
	}

	public void setEntityClass(Class<E> clazz) {
		entityClass = clazz;
	}

	public IBuilderTools getBuilderTools() {
		if (this.builderTools == null) {
			this.builderTools = new BuilderTools();
		}
		return this.builderTools;
	}

	public void setBuilderTools(IBuilderTools delegate) {
		this.builderTools = delegate;
	}
}
