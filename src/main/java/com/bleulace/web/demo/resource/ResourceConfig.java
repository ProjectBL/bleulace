package com.bleulace.web.demo.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.QueryModifierDelegate;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Tree;

@Configuration
class ResourceConfig
{
	@Bean
	@Scope("ui")
	public JPAContainer<AbstractResource> resourceContainer(
			QueryModifierDelegate resourceOwnerQueryModifier)
	{
		JPAContainer<AbstractResource> container = new JPAContainer<AbstractResource>(
				AbstractResource.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<AbstractResource>(
						AbstractResource.class));
		container.setParentProperty("parent");
		container.setQueryModifierDelegate(resourceOwnerQueryModifier);
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