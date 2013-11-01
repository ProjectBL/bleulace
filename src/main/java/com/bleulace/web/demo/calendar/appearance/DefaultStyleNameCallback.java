package com.bleulace.web.demo.calendar.appearance;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;

@Scope("prototype")
@Component
class DefaultStyleNameCallback implements StyleNameCallback
{
	private final String id;

	DefaultStyleNameCallback(String id)
	{
		this.id = id;
	}

	@Override
	public String evaluate(PersistentEvent event)
	{
		return event.getRsvpStatus(id).getStyleName();
	}
}