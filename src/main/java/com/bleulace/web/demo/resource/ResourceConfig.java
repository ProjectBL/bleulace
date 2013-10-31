package com.bleulace.web.demo.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.JPAContainerFactoryBean;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.utils.CallByName;
import com.bleulace.web.SystemUser;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Tree;

@Configuration
class ResourceConfig
{
	@Autowired
	private ResourceDAO dao;

	@Autowired
	private SystemUser user;

	@Bean
	@Scope("ui")
	public JPAContainerFactoryBean<AbstractResource> resourceContainer()
	{
		JPAContainerFactoryBean<AbstractResource> container = new JPAContainerFactoryBean<AbstractResource>(
				AbstractResource.class);
		container
				.setEntityProvider(new TransactionalEntityProvider<AbstractResource>(
						AbstractResource.class));
		container.setParentProperty("parent");
		ResourceQueryModifier modifier = new ResourceQueryModifier();
		modifier.setManagerIds(new CallByName<Collection<String>>()
		{
			@Override
			public Collection<String> evaluate()
			{
				return user.getTargetIds();
			}
		});
		container.setQueryModifierDelegate(modifier);
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