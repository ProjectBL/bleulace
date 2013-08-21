package com.bleulace.domain.management.ui.calendar.view;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.presentation.ScheduleStatusFinder;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContextFactory;
import com.bleulace.domain.management.ui.calendar.modal.CalendarModal;
import com.bleulace.domain.management.ui.calendar.model.EventModelAdapter;
import com.bleulace.domain.management.ui.calendar.model.EventModelFactory;
import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

@Configurable
class CalendarViewPresenter implements CalendarViewListener, Serializable
{
	private final CalendarView view;

	@Autowired
	private transient CalendarViewContextFactory ctxFactory;

	@Autowired
	private transient ScheduleStatusFinder finder;

	@Autowired
	private transient EventModelFactory modelFactory;

	private CalendarViewContext ctx;

	private EventModelAdapter<?> adapter;

	CalendarViewPresenter(CalendarView view)
	{
		this.view = view;
	}

	@Override
	public void visibleDatesChange(Date oldStart, Date oldEnd, Date newStart,
			Date newEnd)
	{
		view.showTimeSlots(finder.findScheduleStatus(ctx.getOwnerId(),
				newStart, newEnd, ctx.getTimeZone()));
	}

	@Override
	public void eventSelected(EventDTO dto)
	{
		view.showModalWindow(this, modelFactory.make(dto, ctx));
	}

	@Override
	public void eventDragged(EventDTO dto, Date newStart, Date newEnd)
	{
		dto.setStart(newStart);
		dto.setEnd(newEnd);
		eventSelected(dto);
	}

	@Override
	public void rangeSelected(Date start, Date end)
	{
		adapter = modelFactory.make(start, end, ctx);
		view.showModalWindow(this, adapter);
	}

	@Override
	public void viewEntered(ViewChangeEvent event)
	{
		ctx = ctxFactory.make(event.getParameters(), SecurityUtils.getSubject()
				.getId());
		view.initialize(ctx);
	}

	@Override
	public void applyModal(CalendarModal modal)
	{
	}

	@Override
	public void cancelModal(CalendarModal modal)
	{
	}

	@Override
	public void timeSlotSelected(LocalTime time, LocalDate start, LocalDate end)
	{
		Notification.show("IMPLEMENT ME!");
	}
}