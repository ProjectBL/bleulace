package com.bleulace.web.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Tree;

@Configuration
class ResourceConfig
{
	@Autowired
	private ResourceDAO dao;

	@Bean
	@Scope("ui")
	public JPAContainer<AbstractResource> resourceContainer()
	{
		JPAContainer<AbstractResource> container = new JPAContainer<AbstractResource>(
				AbstractResource.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<AbstractResource>(
						AbstractResource.class));
		container.setParentProperty("parent");
		return container;
	}

	@Bean
	@Scope("ui")
	public Tree resourceTree(JPAContainer<AbstractResource> resourceContainer,
			@Qualifier("resourceTreeHandler") Handler resourceTreeHandler)
	{
		Tree bean = new Tree("Resources");
		bean.setContainerDataSource(resourceContainer);
		bean.addActionHandler(resourceTreeHandler);
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		return bean;
	}
}