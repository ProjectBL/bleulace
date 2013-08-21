package com.bleulace.domain.management.ui.calendar.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.ui.calendar.component.CalendarComponent;
import com.bleulace.domain.management.ui.calendar.timeslot.TimeSlotComponent;

@Configuration
class CalendarViewConfig
{
	@Bean
	@Scope("session")
	public CalendarView calendarView(CalendarComponent calendarComponent,
			TimeSlotComponent timeSlotComponent)
	{
		CalendarView view = new CalendarViewImpl(calendarComponent,
				timeSlotComponent);
		CalendarViewPresenter presenter = new CalendarViewPresenter(view);
		view.addViewListener(presenter);
		return view;
	}
}