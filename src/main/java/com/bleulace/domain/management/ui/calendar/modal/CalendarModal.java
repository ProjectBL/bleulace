package com.bleulace.domain.management.ui.calendar.modal;

import com.bleulace.domain.management.ui.calendar.model.EventModel;

public interface CalendarModal
{
	void addModalListener(CalendarModalListener listener);

	void setModelCallback(ModelCallback callback);

	public interface ModelCallback
	{
		EventModel getModel();
	}

	public interface CalendarModalListener
	{
		void apply(EventModel model);

		void delete(EventModel model);

		void cancel(EventModel model);
	}
}
