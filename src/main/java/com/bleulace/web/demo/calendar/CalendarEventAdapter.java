package com.bleulace.web.demo.calendar;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.domain.management.model.EventParticipant;
import com.bleulace.domain.management.model.Manager;
import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface CalendarEventAdapter extends EditableCalendarEvent,
		EventChangeNotifier
{
	@NotEmpty
	@Override
	String getCaption();

	@NotEmpty
	@Override
	String getDescription();

	@Valid
	PersistentEvent getSource();

	void setSource(PersistentEvent event);

	List<EventParticipant> getInvitees();

	List<Manager> getAssignments();

	void fireEventChange();

	public interface StyleNameCallback
	{
		String getStyleName(CalendarEventAdapter event);
	}
}