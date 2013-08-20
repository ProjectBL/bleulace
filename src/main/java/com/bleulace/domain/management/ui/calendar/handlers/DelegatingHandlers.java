package com.bleulace.domain.management.ui.calendar.handlers;

import org.springframework.util.Assert;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext.Handlers;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

public class DelegatingHandlers implements Handlers
{
	private DateClickHandler dateClickHandler = new BasicDateClickHandler();
	private EventMoveHandler eventMoveHandler = new BasicEventMoveHandler();
	private EventResizeHandler eventResizeHandler = new BasicEventResizeHandler();
	private RangeSelectHandler rangeSelectHandler = new BasicRangeSelectHandler();

	public DelegatingHandlers()
	{
	}

	public DelegatingHandlers setHandler(DateClickHandler handler)
	{
		Assert.notNull(handler);
		this.dateClickHandler = handler;
		return this;
	}

	public DelegatingHandlers setHandler(EventMoveHandler handler)
	{
		Assert.notNull(handler);
		this.eventMoveHandler = handler;
		return this;
	}

	public DelegatingHandlers setHandler(EventResizeHandler handler)
	{
		Assert.notNull(handler);
		this.eventResizeHandler = handler;
		return this;
	}

	public DelegatingHandlers setHandler(RangeSelectHandler handler)
	{
		Assert.notNull(handler);
		this.rangeSelectHandler = handler;
		return this;
	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		dateClickHandler.dateClick(event);
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		eventMoveHandler.eventMove(event);
	}

	@Override
	public void eventResize(EventResize event)
	{
		eventResizeHandler.eventResize(event);
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		rangeSelectHandler.rangeSelect(event);
	}

	private static class BasicRangeSelectHandler implements RangeSelectHandler
	{
		@Override
		public void rangeSelect(RangeSelectEvent event)
		{
			// TODO Auto-generated method stub
		}
	}
}
