package com.bleulace.ui.web.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.BLCalendarEventProvider;
import com.bleulace.domain.calendar.CalendarEntry;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

public class CalendarViewImpl extends CustomComponent implements CalendarView,
		DateClickHandler, EventClickHandler, ValueChangeListener,
		ClickListener, EventResizeHandler, RangeSelectHandler
{
	private static final long serialVersionUID = -8161583092068261965L;

	private List<CalendarViewListener> listeners = new ArrayList<CalendarViewListener>();

	private final Panel detailPanel = new Panel();

	private final CalendarEntryDetail editor = new CalendarEntryEditor();
	private final CalendarEntryDetail viewer = new CalendarEntryViewer();

	private Calendar calendar;

	private final DateField start = new DateField();
	private final DateField end = new DateField();

	private final Set<Button> calendarTypes = new HashSet<Button>();

	public CalendarViewImpl()
	{
		calendar = new Calendar(new BLCalendarEventProvider(Account.current()));
		calendar.setHandler((EventClickHandler) this);
		calendar.setHandler((DateClickHandler) this);
		calendar.setHandler((EventResizeHandler) this);
		calendar.setHandler((RangeSelectHandler) this);
		calendar.setImmediate(true);
		calendar.setHeight("800px");

		initLayout();

		CalendarPresenter presenter = new CalendarPresenter(this);

		addViewListener(presenter);
		editor.addListener(presenter);
		viewer.addListener(presenter);

		start.addValueChangeListener(this);
		start.setImmediate(true);

		end.addValueChangeListener(this);
		end.setImmediate(true);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		for (CalendarViewListener listener : listeners)
		{
			listener.onBoundariesSelected(new Date(), new Date());
		}
	}

	@Override
	public void setBoundaries(Date start, Date end)
	{
		calendar.setStartDate(start);
		calendar.setEndDate(end);
		this.start.setValue(start);
		this.end.setValue(end);
	}

	@Override
	public void showEntryDetail(CalendarEntry entry, Boolean editable)
	{
		CalendarEntryDetail toShow = editable ? editor : viewer;
		toShow.showEntry(entry);
		detailPanel.setContent((Component) toShow);
	}

	@Override
	public void hideEntryDetail()
	{
		detailPanel.setCaption(null);
		detailPanel.setContent(null);
	}

	@Override
	public void addViewListener(CalendarViewListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void setActiveTab(CalendarType type)
	{
		for (Button button : calendarTypes)
		{
			button.setEnabled(type == null ? true : !((CalendarType) button
					.getData()).equals(type));
		}
	}

	@Override
	public void addEntryToCalendar(CalendarEntry entry)
	{
		calendar.addEvent(entry);
	}

	@Override
	public void eventClick(EventClick event)
	{
		for (CalendarViewListener listener : listeners)
		{
			CalendarEntry entry = (CalendarEntry) event.getCalendarEvent();
			System.out.println(entry.getCaption());
			listener.onEntrySelected((CalendarEntry) event.getCalendarEvent());
		}
	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		for (CalendarViewListener listener : listeners)
		{
			listener.onDateClicked(event.getDate());
		}
		new BasicDateClickHandler().dateClick(event);
	}

	@Override
	public void valueChange(ValueChangeEvent event)
	{
		for (CalendarViewListener listener : listeners)
		{
			listener.onRangeSelected(start.getValue(), end.getValue());
		}
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		for (CalendarViewListener listener : listeners)
		{
			listener.onTabSelected((CalendarType) event.getButton().getData());
		}
	}

	private void initLayout()
	{
		HorizontalLayout menu = new HorizontalLayout();
		menu.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		menu.addComponents(start, end);
		for (final CalendarType type : CalendarType.values())
		{
			Button button = new Button(type.toString(), this);
			button.setData(type);
			calendarTypes.add(button);
			menu.addComponent(button);
		}

		HorizontalLayout hLayout = new HorizontalLayout(detailPanel, calendar);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		mainLayout.addComponents(menu, hLayout);

		setCompositionRoot(mainLayout);
	}

	@Override
	public void eventResize(EventResize event)
	{
		EditableCalendarEvent editable = (EditableCalendarEvent) event
				.getCalendarEvent();
		editable.setStart(event.getNewStart());
		editable.setEnd(event.getNewEnd());
		calendar.addEvent(event.getCalendarEvent());
		// initLayout();
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		for (CalendarViewListener listener : listeners)
		{

		}
	}
}