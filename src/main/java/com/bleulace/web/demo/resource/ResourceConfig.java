package com.bleulace.web.demo.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;

import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Tree;

@Configuration
class ResourceConfig
{
	@Autowired
	private ResourceDAO dao;

	@Bean
	public Converter<Collection<String>, Collection<String>> managedResourceFinder()
	{
		return new Converter<Collection<String>, Collection<String>>()
		{
			@Override
			public Collection<String> convert(Collection<String> source)
			{
				return dao.findIds(source);
			}
		};
	}

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
	public Tree resourceTree(JPAContainer<AbstractResource> resourceContainer)
	{
		Tree bean = new Tree("Resources");
		bean.setContainerDataSource(resourceContainer);
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		return bean;
	}
}