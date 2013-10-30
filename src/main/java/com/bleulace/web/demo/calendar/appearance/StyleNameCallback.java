package com.bleulace.web.demo.calendar.appearance;

import com.bleulace.domain.management.model.PersistentEvent;

public interface StyleNameCallback
{
	public String evaluate(PersistentEvent event);
}