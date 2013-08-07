package com.bleulace.utils.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.JodaTimeConverters;

import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

public interface BasicEventMixin extends EditableCalendarEvent,
		EventChangeNotifier
{
	static aspect Impl
	{
		private String BasicEventMixin.caption = "";
		private String BasicEventMixin.description = "";
		private Date BasicEventMixin.end;
		private Date BasicEventMixin.start;
		private String BasicEventMixin.styleName;
		private transient List<EventChangeListener> BasicEventMixin.listeners = new ArrayList<EventChangeListener>();

		private static final Converter<Date, LocalDateTime> JODA_CONVERTER = JodaTimeConverters.DateToLocalDateTimeConverter.INSTANCE;

		public String BasicEventMixin.getCaption()
		{
			return caption;
		}

		public String BasicEventMixin.getDescription()
		{
			return description;
		}

		public Date BasicEventMixin.getEnd()
		{
			return end;
		}

		public Date BasicEventMixin.getStart()
		{
			return start;
		}

		public String BasicEventMixin.getStyleName()
		{
			return styleName;
		}

		public boolean BasicEventMixin.isAllDay()
		{
			Days lengthInDays = Days.daysBetween(
					JODA_CONVERTER.convert(this.getStart()),
					JODA_CONVERTER.convert(this.getEnd()));
			return lengthInDays.equals(Days.ONE)
					|| lengthInDays.isGreaterThan(Days.ONE);
		}

		public void BasicEventMixin.setCaption(String caption)
		{
			this.caption = caption;
			this.fireEventChange();
		}

		public void BasicEventMixin.setDescription(String description)
		{
			this.description = description;
			this.fireEventChange();
		}

		public void BasicEventMixin.setEnd(Date end)
		{
			this.end = end;
			this.fireEventChange();
		}

		public void BasicEventMixin.setStart(Date start)
		{
			this.start = start;
			this.fireEventChange();
		}

		public void BasicEventMixin.setStyleName(String styleName)
		{
			this.styleName = styleName;
			this.fireEventChange();
		}

		public void BasicEventMixin.addEventChangeListener(
				EventChangeListener listener)
		{
			listeners.add(listener);
		}

		public void BasicEventMixin.removeEventChangeListener(
				EventChangeListener listener)
		{
			listeners.remove(listener);
		}

		public void BasicEventMixin.setAllDay(boolean allDay)
		{
			// doesn't do anything
		}

		void BasicEventMixin.fireEventChange()
		{
			EventChangeEvent event = new EventChangeEvent(this);

			for (EventChangeListener listener : listeners)
			{
				listener.eventChange(event);
			}
		}
	}
}