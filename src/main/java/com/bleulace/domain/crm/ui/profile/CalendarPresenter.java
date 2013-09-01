package com.bleulace.domain.crm.ui.profile;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.EventDTOFactory;
import com.bleulace.web.stereotype.Presenter;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;

@Presenter(viewNames = "profileView")
class CalendarPresenter
{
	@Autowired
	private ProfileView view;

	@Autowired
	private EventDTOFactory factory;

	@EventHandler
	public void on(RangeSelectEvent event)
	{
		EventDTO dto = factory.make();
		dto.setStart(event.getStart());
		dto.setEnd(event.getEnd());
		UI.getCurrent().addWindow(new EventWindow(dto));
	}

	@EventHandler
	public void on(EventClick event)
	{
		UI.getCurrent().addWindow(
				new EventWindow((EventDTO) event.getCalendarEvent()));
	}
}