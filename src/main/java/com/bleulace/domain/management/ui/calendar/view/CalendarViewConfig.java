package com.bleulace.domain.management.ui.calendar.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bleulace.domain.management.ui.calendar.view.CalendarView.CalendarViewListener;
import com.bleulace.web.stereotype.UIComponent;

@UIComponent
@Configuration
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