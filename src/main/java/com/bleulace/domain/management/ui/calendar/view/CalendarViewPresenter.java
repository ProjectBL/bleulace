package com.bleulace.domain.management.ui.calendar.view;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.command.EditEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;
import com.bleulace.domain.management.ui.calendar.view.CalendarView.EventDTOCommandCallback;
import com.bleulace.utils.dto.DTOFactory;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Configurable
class CalendarViewPresenter implements CommandGatewayAware,
		CalendarViewListener, Serializable
{
	private final CalendarView view;

	@Autowired
	private DTOFactory<EventDTO> eventDTOFactory;

	private static final CreateCallback CREATE_CALLBACK = new CreateCallback();
	private static final EditCallback EDIT_CALLBACK = new EditCallback();

	CalendarViewPresenter(CalendarView view)
	{
		this.view = view;
	}

	@Override
	public void eventSelected(EventDTO dto)
	{
		view.showModal(dto, EDIT_CALLBACK);
	}

	@Override
	public void eventMoved(EventDTO dto, Date newStart, Date newEnd)
	{
		if (newStart.after(new Date()))
		{
			dto.setStart(newStart);
			dto.setEnd(newEnd);
			sendAndWait(EDIT_CALLBACK.getCommand(dto));
		}
		else
		{
			Notification.show(
					"Can not schedule an event in the past. Aborting.",
					Type.WARNING_MESSAGE);
		}
		view.refreshCalendar();
	}

	@Override
	public void rangeSelected(Date start, Date end)
	{
		EventDTO dto = eventDTOFactory.make();
		dto.setStart(start);
		dto.setEnd(end);
		view.showModal(dto, CREATE_CALLBACK);
	}

	@Override
	public void calendarTypeChanged(CalendarType type)
	{
		Range<Date> range = type.convert(new Date());
		view.setVisibleDates(range.getMinimum(), range.getMaximum());
	}

	private static class CreateCallback implements
			EventDTOCommandCallback<CreateEventCommand>
	{
		@Override
		public CreateEventCommand getCommand(EventDTO dto)
		{
			return new CreateEventCommand(dto.getCaption(),
					dto.getDescription(), dto.getStart(), dto.getEnd());
		}
	}

	private static class EditCallback implements
			EventDTOCommandCallback<EditEventCommand>
	{
		@Override
		public EditEventCommand getCommand(EventDTO dto)
		{
			return new EditEventCommand(dto.getId(), dto.getCaption(),
					dto.getDescription(), dto.getStart(), dto.getEnd());
		}
	}
}