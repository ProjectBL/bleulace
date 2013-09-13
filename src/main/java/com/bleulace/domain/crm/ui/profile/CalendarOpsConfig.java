package com.bleulace.domain.crm.ui.profile;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.bleulace.domain.management.ui.calendar.CalendarType;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;

@UIComponent
class CalendarOpsConfig implements ClickListener
{
	private Button forward = new Button("Next", this);
	private Button back = new Button("Prev", this);

	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@Bean(name = "calendarOps")
	public Component calendarOps(EventSearchField searchField)
	{
		forward.setData(new ForwardEvent(calendar));
		back.setData(new BackwardEvent(calendar));

		Component rangeOps = new RangeOps();
		HorizontalLayout ops = new HorizontalLayout(back, rangeOps, forward);
		rangeOps.setSizeUndefined();

		ops.setComponentAlignment(back, Alignment.BOTTOM_LEFT);
		ops.setComponentAlignment(rangeOps, Alignment.BOTTOM_CENTER);
		ops.setComponentAlignment(forward, Alignment.BOTTOM_RIGHT);
		ops.setWidth(100f, Unit.PERCENTAGE);

		return new VerticalLayout(searchField, ops);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event.getButton()
				.getData()));
	}

	private class RangeOps extends CustomComponent
	{
		RangeOps()
		{
			HorizontalLayout buttons = new HorizontalLayout();

			for (CalendarType type : CalendarType.values())
			{
				buttons.addComponent(new Button(
						type == CalendarType.DAY ? "Today" : type.toString(),
						new CalendarTypeButtonListener(type)));
			}

			setCompositionRoot(buttons);
		}

		private class CalendarTypeButtonListener implements
				Button.ClickListener
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
						.convert(type == CalendarType.DAY ? new Date()
								: calendar.getStartDate());
				calendar.setStartDate(range.getMinimum());
				calendar.setEndDate(range.getMaximum());
			}
		}
	}
}