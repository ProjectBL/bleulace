package com.bleulace.domain.management.ui.calendar.timeslot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.presentation.ScheduleStatus;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class TimeSlotPanels extends CustomComponent implements TimeSlotComponent
{
	private Map<ScheduleStatus, TimeSlotPanel> panels = new HashMap<ScheduleStatus, TimeSlotPanel>();

	TimeSlotPanels()
	{
		VerticalLayout layout = new VerticalLayout();
		for (ScheduleStatus status : ScheduleStatus.values())
		{
			TimeSlotPanel panel = new TimeSlotPanel(status);
			panels.put(status, panel);
			layout.addComponentAsFirst(panel);
		}
		Panel panel = new Panel(layout);
		setCompositionRoot(panel);
	}

	@Override
	public void showTimeSlots(Map<LocalTime, ScheduleStatus> timeSlots)
	{
		// erase old values
		clearTimeSlots();

		// initialize map
		Map<ScheduleStatus, List<LocalTime>> map = new HashMap<ScheduleStatus, List<LocalTime>>();
		for (ScheduleStatus key : ScheduleStatus.values())
		{
			map.put(key, new ArrayList<LocalTime>());
		}

		// populate map
		for (Entry<LocalTime, ScheduleStatus> entry : timeSlots.entrySet())
		{
			map.get(entry.getValue()).add(entry.getKey());
		}

		// dump map contents to ui
		for (ScheduleStatus key : ScheduleStatus.values())
		{
			panels.get(key).setTimeSlots(map.get(key));
		}
	}

	@Override
	public void clearTimeSlots()
	{
		for (TimeSlotPanel panel : panels.values())
		{
			panel.clearTimeSlots();
		}
	}

	@Override
	public void addListener(TimeSlotListener listener)
	{
		for (TimeSlotPanel panel : panels.values())
		{
			panel.addComponentListener(listener);
		}
	}
}
