package com.bleulace.domain.crm.ui.profile;

import java.util.Date;

import org.apache.commons.lang3.Range;

import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

class CalendarOperations extends CustomComponent
{
	private final Calendar calendar;

	CalendarOperations(Calendar calendar)
	{
		this.calendar = calendar;

		HorizontalLayout buttons = new HorizontalLayout();

		for (CalendarType type : CalendarType.values())
		{
			buttons.addComponent(new Button(type == CalendarType.DAY ? "Today"
					: type.toString(), new CalendarTypeButtonListener(type)));
		}

		setCompositionRoot(buttons);
	}

	private class CalendarTypeButtonListener implements Button.ClickListener
	{
		private final CalendarType type;

		CalendarTypeButtonListener(CalendarType type)
		{
			this.type = type;
		}

		@Override
		public void buttonClick(ClickEvent event)
		{
			Range<Date> range = type
					.convert(type == CalendarType.DAY ? new Date() : calendar
							.getStartDate());
			calendar.setStartDate(range.getMinimum());
			calendar.setEndDate(range.getMaximum());
		}
	}
}