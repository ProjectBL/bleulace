package com.bleulace.web.demo.resource;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.web.annotation.VaadinView;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;

@VaadinView
class ResourceView extends CustomComponent implements View
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private JPAContainer<AbstractResource> resourceContainer;

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		Tree tree = ctx.getBean(Tree.class);
		tree.setContainerDataSource(resourceContainer);
		// resourceContainer.addContainerFilter("assignments.account.id",
		// SpringApplicationContext.getUser().getId(), false, false);
		setCompositionRoot(tree);
	}
}
