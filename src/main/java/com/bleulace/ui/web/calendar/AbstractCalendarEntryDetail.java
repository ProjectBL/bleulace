package com.bleulace.ui.web.calendar;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.Reindeer;

@Configurable
public abstract class AbstractCalendarEntryDetail extends CustomComponent
		implements CalendarEntryDetail
{
	private static final long serialVersionUID = -6088166085028374840L;

	private Set<CalendarEntryDetailListener> listeners = new HashSet<CalendarEntryDetailListener>();

	@PostConstruct
	public void init()
	{
		addStyleName(Reindeer.LAYOUT_BLUE);
		setHeight("800px");
	}

	public Set<CalendarEntryDetailListener> listeners()
	{
		return listeners;
	}

	@Override
	public void addListener(CalendarEntryDetailListener listener)
	{
		listeners.add(listener);
	}
}
