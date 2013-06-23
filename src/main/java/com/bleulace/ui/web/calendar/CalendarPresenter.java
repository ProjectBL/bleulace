package com.bleulace.ui.web.calendar;

import java.util.Date;

import org.apache.commons.lang3.Range;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.calendar.JPACalendarEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

public class CalendarPresenter implements EventClickHandler, RangeSelectHandler
{
	private static final long serialVersionUID = 7715084569870641483L;

	private final Calendar calendar;

	public CalendarPresenter(Calendar calendar)
	{
		this.calendar = calendar;
		calendar.setHandler((EventClickHandler) this);
		calendar.setHandler((RangeSelectHandler) this);
	}

	public void showCalendarType(CalendarType type)
	{
		Range<Date> range = type.getDateRange(new Date());
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());
	}

	@Override
	public void eventClick(EventClick event)
	{
		UI.getCurrent().addWindow(
				new EventEditor((JPACalendarEvent) event.getCalendarEvent()));
	}

	private void showEventRescheduledMessage(CalendarEvent event)
	{
		String caption = event.getCaption() + " has been rescheduled.";
		String description = event.getStart() + " " + event.getEnd();
		Notification.show(caption, description, Type.TRAY_NOTIFICATION);
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		JPACalendarEvent calendarEvent = new JPACalendarEvent();
		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());
		UI.getCurrent().addWindow(new EventEditor(calendarEvent));
	}

	class EventEditor extends Window implements ClickListener
	{
		private static final long serialVersionUID = -464672570279240167L;

		private final BeanFieldGroup<JPACalendarEvent> fieldGroup;

		public EventEditor(JPACalendarEvent event)
		{
			fieldGroup = new BeanFieldGroup<JPACalendarEvent>(
					JPACalendarEvent.class);
			BeanItem<JPACalendarEvent> item = new BeanItem<JPACalendarEvent>(
					event);
			fieldGroup.setItemDataSource(item);

			setModal(true);
			setWidth("300px");
			setCaption("Edit: " + event.getCaption());

			AccountField accountField = new AccountField("Participants");
			fieldGroup.bind(accountField, "accounts");
			accountField.setWidth("160px");

			TextField tf = fieldGroup.buildAndBind("Title", "caption",
					TextField.class);
			tf.setWidth("160px");

			TextArea ta = fieldGroup.buildAndBind("Location", "description",
					TextArea.class);
			ta.setWidth("160px");

			Button button = new Button("Submit", this);
			//@formatter:off
			FormLayout layout = new FormLayout(
					tf,
					ta,
					accountField,
					button);
			//@formatter:on
			setContent(layout);
		}

		@Override
		@Transactional
		public void buttonClick(ClickEvent event)
		{
			try
			{
				fieldGroup.commit();
				Notification.show("Event saved to database");
				close();
			}
			catch (CommitException e)
			{
				e.printStackTrace();
			}
		}
	}
}