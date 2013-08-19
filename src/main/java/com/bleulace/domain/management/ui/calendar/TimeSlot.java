package com.bleulace.domain.management.ui.calendar;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.CalendarView.CalendarViewListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

class TimeSlot extends CustomComponent implements ClickListener
{
	private List<CalendarViewListener> listeners = new LinkedList<CalendarViewListener>();

	private final LocalTime time;
	private final ScheduleStatus status;

	TimeSlot(LocalTime time, ScheduleStatus status)
	{
		this.time = time;
		this.status = status;
		HorizontalLayout layout = new HorizontalLayout(new Label(
				time.toString()));
		if (!status.equals(ScheduleStatus.BUSY))
		{
			layout.addComponent(new Button("Schedule", this));
		}
		setCompositionRoot(layout);
	}

	public LocalTime getTime()
	{
		return time;
	}

	public ScheduleStatus getStatus()
	{
		return status;
	}

	public void addListener(CalendarViewListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		fireEvent();
	}

	private void fireEvent()
	{
		for (CalendarViewListener listener : listeners)
		{
			listener.timeslotSelect(this);
		}
	}
}
