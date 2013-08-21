package com.bleulace.domain.management.ui.calendar.model;

import java.util.List;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface EventModel extends EditableCalendarEvent
{
	String getId();

	public CalendarViewContext getContext();

	List<String> getInviteeIds();

	void setInviteeIds(List<String> inviteeIds);

	boolean isNew();

	public interface EventModelCallback
	{
		void execute(EventModel model);
	}
}