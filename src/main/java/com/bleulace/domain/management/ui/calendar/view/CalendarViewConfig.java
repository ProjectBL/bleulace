package com.bleulace.domain.management.ui.calendar.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;

@Configuration
class CalendarViewConfig
{
	@Bean
	@Scope("session")
	public CalendarView calendarView()
	{
		CalendarView view = new CalendarViewImpl();
		CalendarViewListener presenter = new CalendarViewPresenter(view);
		view.addViewListener(presenter);
		return view;
	}
}