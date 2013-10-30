package com.bleulace.web.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bleulace.web.SystemUser;
import com.bleulace.web.demo.calendar.ViewTargetChangedEvent;
import com.google.common.eventbus.Subscribe;
import com.vaadin.addon.jpacontainer.JPAContainer;

@Component
public class ViewTargetChangedListener
{
	@Autowired
	private SystemUser user;

	@Autowired
	private ApplicationContext ctx;

	@Subscribe
	public void subscribe(ViewTargetChangedEvent event)
	{
		if (user.getId().equals(event.getUserId()))
		{
			ctx.getBean("resourceContainer", JPAContainer.class).refresh();
		}
	}
}
