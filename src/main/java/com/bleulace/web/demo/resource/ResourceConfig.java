package com.bleulace.web.demo.resource;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Tree;

@Configuration
class ResourceConfig
{
	@Bean
	@Scope("prototype")
	public JPAContainer<AbstractResource> resourceContainer()
	{
		JPAContainer<AbstractResource> container = new JPAContainer<AbstractResource>(
				AbstractResource.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<AbstractResource>(
						AbstractResource.class));
		container.setParentProperty("parent");
		container.setQueryModifierDelegate(new DefaultQueryModifierDelegate()
		{
			@Override
			@RequiresUser
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder,
					CriteriaQuery<?> query, List<Predicate> predicates)
			{
				Root<?> r = query.getRoots().iterator().next();
				Join<?, ?> c = r.join("children", JoinType.LEFT);
				predicates.add(criteriaBuilder.equal(c.type(),
						ManagementAssignment.class));
			}
		});
		return container;
	}

	@Bean
	@Scope("ui")
	public Tree resourceTree(JPAContainer<AbstractResource> container)
	{
		Tree bean = new Tree("Resources");
		bean.setContainerDataSource(container);
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		return bean;
	}
}