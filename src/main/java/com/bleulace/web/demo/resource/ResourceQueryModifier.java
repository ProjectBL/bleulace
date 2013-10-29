package com.bleulace.web.demo.resource;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;

@Component
@Scope("prototype")
class ResourceQueryModifier extends DefaultQueryModifierDelegate implements
		ResourceQuery<JPAContainer<AbstractResource>>
{
	private Set<String> managerIds;

	private final JPAContainer<AbstractResource> container;

	@Autowired
	private Converter<Collection<String>, Collection<String>> managedResourceFinder;

	ResourceQueryModifier()
	{
		container = new JPAContainer<AbstractResource>(AbstractResource.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<AbstractResource>(
						AbstractResource.class));
		container.setParentProperty("parent");
		container.setQueryModifierDelegate(this);
	}

	@Override
	public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> query, List<Predicate> predicates)
	{
		if (managerIds != null)
		{
			final Root<?> r = query.getRoots().iterator().next();
			predicates.add(r.get("id").in(
					managedResourceFinder.convert(managerIds)));
		}
	}

	@Override
	public Set<String> getManagerIds()
	{
		return managerIds;
	}

	@Override
	public void setManagerIds(Set<String> managerIds)
	{
		this.managerIds = managerIds;
	}

	@Override
	public JPAContainer<AbstractResource> getContainer()
	{
		return container;
	}
}
