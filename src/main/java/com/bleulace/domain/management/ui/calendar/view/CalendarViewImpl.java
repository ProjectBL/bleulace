package com.bleulace.domain.management.ui.calendar.view;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.component.CalendarComponent;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.modal.ModalFactory;
import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.bleulace.domain.management.ui.calendar.timeslot.TimeSlotComponent;
import com.bleulace.domain.management.ui.calendar.timeslot.TimeSlotComponent.TimeSlotListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Configurable
class CalendarViewImpl extends CustomComponent implements CalendarView, View,
		TimeSlotListener
{
	private final List<CalendarViewListener> listeners = new LinkedList<CalendarViewListener>();

	private Label title = new Label("");

	private CalendarComponent calendarComponent;

	private TimeSlotComponent timeSlotComponent;

	@Autowired
	private ModalFactory modalFactory;

	CalendarViewImpl(CalendarComponent calendarComponent,
			TimeSlotComponent timeSlotComponent)
	{
		this.calendarComponent = calendarComponent;
		this.timeSlotComponent = timeSlotComponent;

		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent((com.vaadin.ui.Component) calendarComponent);

		// TODO : fix me
		// timeSlotComponent.addListener(this);
		// layout.addComponent(panels);

		setCompositionRoot(layout);
	}

	@Override
	public Range<Date> getRange()
	{
		return calendarComponent.getRange();
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		for (CalendarViewListener l : listeners)
		{
			l.viewEntered(event);
		}
	}

	@Override
	public void initialize(CalendarViewContext ctx)
	{
		calendarComponent.initialize(ctx);
	}

	@Override
	public void timeSelected(LocalTime time)
	{
		for (CalendarViewListener l : listeners)
		{
			Range<Date> range = calendarComponent.getRange();
			l.timeSlotSelected(time,
					LocalDate.fromDateFields(range.getMinimum()),
					LocalDate.fromDateFields(range.getMaximum()));
		}
	}

	@Override
	public void setTitle(String title)
	{
		this.title.setCaption(title);
	}

	@Override
	public void showTimeSlots(Map<LocalTime, ScheduleStatus> timeSlots)
	{
		timeSlotComponent.showTimeSlots(timeSlots);
	}

	@Override
	public void addViewListener(CalendarViewListener listener)
	{
		listeners.add(listener);
		calendarComponent.addListener(listener);
	}

	@Override
	public void showModalWindow(CalendarViewListener listener, EventModel model)
	{
	}
}