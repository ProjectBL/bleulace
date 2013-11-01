package com.bleulace.web.demo.calendar.appearance;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;

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
		RsvpStatus status = event.getRsvpStatus(id);
		return status == null ? null : status.getStyleName();
	}
}