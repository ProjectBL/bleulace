package com.bleulace.web.demo.resource;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.web.annotation.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;

@VaadinView
class ResourceView extends CustomComponent implements View
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		Tree tree = ctx.getBean(Tree.class);
		setCompositionRoot(tree);
	}
}
