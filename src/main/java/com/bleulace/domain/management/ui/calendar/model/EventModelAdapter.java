package com.bleulace.domain.management.ui.calendar.model;

import javax.validation.Valid;

public interface EventModelAdapter<T> extends EventModel
{
	@Valid
	public T getAdaptedCommand();
}
