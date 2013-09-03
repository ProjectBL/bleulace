package com.bleulace.domain.crm.ui.profile;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

@UIComponent
class CalendarRangeOps extends CustomComponent
{
	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	CalendarRangeOps()
	{
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