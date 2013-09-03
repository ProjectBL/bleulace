package com.bleulace.domain.crm.ui.profile;

import javax.annotation.PostConstruct;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;

@UIComponent
class ProfileCalendarHandlers implements EventClickHandler, RangeSelectHandler,
		EventMoveHandler, EventResizeHandler, ForwardHandler, BackwardHandler
{
	@Autowired
	@Qualifier("uiBus")
	private EventBus uiBus;

	@PostConstruct
	protected void init()
	{
		AnnotationEventListenerAdapter.subscribe(this, uiBus);
	}

	@Override
	public void eventClick(EventClick event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	public void eventResize(EventResize event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	@EventHandler
	public void backward(BackwardEvent event)
	{
		new BasicBackwardHandler().backward(event);
	}

	@Override
	@EventHandler
	public void forward(ForwardEvent event)
	{
		new BasicForwardHandler().forward(event);
	}
}
