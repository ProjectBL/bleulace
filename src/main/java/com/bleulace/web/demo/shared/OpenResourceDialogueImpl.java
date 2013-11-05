package com.bleulace.web.demo.shared;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vaadin.addon.jpacontainer.JPAContainer;

@Component("openResourceDialogue")
@Scope("prototype")
class OpenResourceDialogueImpl implements OpenResourceDialogue
{
	@Autowired
	private ApplicationContext ctx;

	private String title;
	private JPAContainer<?> container;

	private final List<OpenResourceListener> listeners = new ArrayList<OpenResourceListener>();

	@Override
	public void addOpenResourceListener(OpenResourceListener listener)
	{
		Assert.notNull(listener);
		listeners.add(listener);
	}

	@Override
	public void show()
	{
		Assert.notNull(title);
		Assert.notNull(container);
		ctx.getBean("openDialogue", title, container, listeners);
	}

	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public void setContainer(JPAContainer<?> container)
	{
		this.container = container;
	}
}