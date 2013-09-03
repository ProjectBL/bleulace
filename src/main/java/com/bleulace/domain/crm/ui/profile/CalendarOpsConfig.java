package com.bleulace.domain.crm.ui.profile;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;

@UIComponent
class CalendarOpsConfig implements ClickListener
{
	private Button forward = new Button("Next", this);
	private Button back = new Button("Prev", this);

	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@Bean(name = "calendarOps")
	public Component calendarOps(CalendarRangeOps rangeOps,
			EventSearchField searchField,
			@Qualifier("profileCalendar") Calendar calendar)
	{
		forward.setData(new ForwardEvent(calendar));
		back.setData(new BackwardEvent(calendar));

		HorizontalLayout mid = new HorizontalLayout(rangeOps, searchField);
		HorizontalLayout bean = new HorizontalLayout(back, mid, forward);
		bean.setComponentAlignment(back, Alignment.BOTTOM_LEFT);
		bean.setComponentAlignment(mid, Alignment.BOTTOM_CENTER);
		bean.setComponentAlignment(forward, Alignment.BOTTOM_RIGHT);
		return bean;
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event.getButton()
				.getData()));
	}
}
