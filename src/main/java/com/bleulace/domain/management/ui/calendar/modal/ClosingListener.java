package com.bleulace.domain.management.ui.calendar.modal;

import com.bleulace.domain.management.ui.calendar.modal.CalendarModal.CalendarModalListener;
import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.vaadin.ui.Window;

class ClosingListener implements CalendarModalListener
{
	private final Window window;

	public ClosingListener(Window window)
	{
		this.window = window;
	}

	@Override
	public void apply(EventModel model)
	{
	}

	@Override
	public void delete(EventModel model)
	{
	}

	@Override
	public void cancel(EventModel model)
	{
		window.close();
	}
}