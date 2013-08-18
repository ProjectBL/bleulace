package com.bleulace.domain.management.ui.calendar.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.CancelEventCommand;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventFinder;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Configurable
class EventDTOProvider extends BasicEventProvider implements
		CommandGatewayAware
{
	private final CalendarViewContext context;

	@Autowired
	private EventFinder finder;

	EventDTOProvider(CalendarViewContext context)
	{
		this.context = context;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate)
	{
		List<CalendarEvent> activeEvents = new ArrayList<CalendarEvent>();
		for (EventDTO dto : finder.findByAccountForDates(context.getOwnerId(),
				startDate, endDate))
		{
			context.decorate(dto);
			eventList.add(dto);
			activeEvents.add(dto);
		}
		return activeEvents;
	}

	@Override
	public void addEvent(CalendarEvent event)
	{
		sendAndWait(new CreateEventCommand(event.getCaption(),
				event.getDescription(), event.getStart(), event.getEnd()));
		((EventDTO) event).addEventChangeListener(this);
		super.addEvent(event);
	}

	@Override
	public void removeEvent(CalendarEvent event)
	{
		sendAndWait(new CancelEventCommand(((EventDTO) event).getId()));
		((EventDTO) event).removeEventChangeListener(this);
		super.removeEvent(event);
	}
}
