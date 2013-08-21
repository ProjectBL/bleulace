package com.bleulace.domain.management.ui.calendar.timeslot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalTime;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.bleulace.domain.management.ui.calendar.timeslot.TimeSlotComponent.TimeSlotListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

class TimeSlotPanel extends Panel
{
	private List<TimeSlotListener> listeners = new LinkedList<TimeSlotListener>();

	private final ScheduleStatus status;

	private final VerticalLayout layout = new VerticalLayout();

	TimeSlotPanel(ScheduleStatus status)
	{
		this.status = status;
		setCaption(status.toString());
		setContent(layout);
	}

	void clearTimeSlots()
	{
		layout.removeAllComponents();
	}

	void setTimeSlots(List<LocalTime> times)
	{
		clearTimeSlots();
		Collections.sort(times);
		for (LocalTime time : times)
		{
			layout.addComponent(new TimeSlot(time));
		}
	}

	void addComponentListener(TimeSlotListener listener)
	{
		listeners.add(listener);
	}

	private class TimeSlot extends CustomComponent implements ClickListener
	{
		private final LocalTime time;

		TimeSlot(LocalTime time)
		{
			this.time = time;

			HorizontalLayout layout = new HorizontalLayout();

			Label label = new Label(time.toString());
			layout.addComponent(label);
			layout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
			if (status != ScheduleStatus.BUSY)
			{
				Button button = new Button("schedule", this);
				layout.addComponent(button);
				layout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
			}
			setCompositionRoot(layout);
		}

		@Override
		public void buttonClick(ClickEvent event)
		{
			for (TimeSlotListener l : listeners)
			{
				l.timeSelected(time);
			}
		}
	}
}