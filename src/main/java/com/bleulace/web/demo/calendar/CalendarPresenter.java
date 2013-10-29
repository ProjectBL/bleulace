package com.bleulace.web.demo.calendar;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.web.annotation.Presenter;
import com.bleulace.web.demo.calendar.span.CalendarSpan;
import com.vaadin.ui.Calendar;

@Presenter
public class CalendarPresenter
{
	@Autowired
	private Calendar calendar;

	public void spanSelected(CalendarSpan span)
	{
		Assert.notNull(span);
		span.resize(calendar);
	}

	public void dateSelected(Date date)
	{
		calendar.setStartDate(date);
		spanSelected(CalendarSpan.DAY);
	}
}