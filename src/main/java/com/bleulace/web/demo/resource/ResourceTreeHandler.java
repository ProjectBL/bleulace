package com.bleulace.web.demo.resource;

import org.springframework.stereotype.Component;

import com.bleulace.web.annotation.WebProfile;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;

@WebProfile
@Component
class ResourceTreeHandler implements Handler
{
	private final Action[] actions = new Action[] { new Action("foo") };

	@Override
	public Action[] getActions(Object target, Object sender)
	{
		return actions;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		// TODO Auto-generated method stub

	}
}