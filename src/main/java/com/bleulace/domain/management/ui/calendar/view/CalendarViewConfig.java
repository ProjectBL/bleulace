package com.bleulace.domain.management.ui.calendar.view;

import org.springframework.context.annotation.Bean;

import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;

class CalendarViewConfig
{
	@Bean
	public CalendarView calendarView()
	{
		CalendarView view = new CalendarViewImpl();
		CalendarViewListener presenter = new CalendarViewPresenter(view);
		view.addViewListener(presenter);
		return view;
	}
}