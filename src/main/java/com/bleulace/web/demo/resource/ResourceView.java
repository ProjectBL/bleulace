package com.bleulace.web.demo.resource;

import java.util.Collections;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.utils.ctx.SpringApplicationContext;
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

	@Autowired
	private ResourceQuery<?> query;

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		Tree tree = ctx.getBean(Tree.class);
		tree.setContainerDataSource(query.getContainer());
		setCompositionRoot(tree);
		query.setManagerIds(Collections.singleton(SpringApplicationContext
				.getUser().getId()));
	}
}
