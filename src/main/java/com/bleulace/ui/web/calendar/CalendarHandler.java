package com.bleulace.ui.web.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.calendar.JPACalendarEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import com.vaadin.ui.themes.Reindeer;

@Configurable
class CalendarHandler extends CustomComponent implements EventClickHandler,
		RangeSelectHandler, EventMoveHandler, EventResizeHandler,
		DateClickHandler, CloseListener
{
	private static final long serialVersionUID = 7715084569870641483L;

	private List<CalendarHandlerListener> listeners = new ArrayList<CalendarHandlerListener>();

	private final CalendarType type;

	private final JPACalendarEventProvider provider;

	private Date cursor = new Date();

	public CalendarHandler(JPACalendarEventProvider provider, CalendarType type)
	{
		this.provider = provider;
		this.type = type;
		init();
	}

	public void setCursor(Date cursor)
	{
		this.cursor = cursor;
		init();
	}

	@Override
	public void dateClick(DateClickEvent event)
	{
		for (CalendarHandlerListener listener : listeners)
		{
			listener.onCursorSet(event.getDate());
		}
	}

	@Override
	public void eventClick(EventClick event)
	{
		UI.getCurrent().addWindow(
				new EventEditor((JPACalendarEvent) event.getCalendarEvent(),
						event.getComponent()));
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		JPACalendarEvent calendarEvent = new JPACalendarEvent();
		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());
		EventEditor editor = new EventEditor(calendarEvent,
				event.getComponent());
		editor.setCaption("Create Event");
		UI.getCurrent().addWindow(editor);
	}

	@Override
	public void eventResize(EventResize event)
	{
		CalendarEvent calendarEvent = event.getCalendarEvent();

		if (!(event.getNewEnd().equals(calendarEvent.getEnd()) && event
				.getNewStart().equals(calendarEvent.getStart())))
		{
			reschedule(calendarEvent, event.getNewStart(), event.getNewEnd(),
					event.getComponent());
		}
	}

	@Override
	public void eventMove(MoveEvent event)
	{
		CalendarEvent calendarEvent = event.getCalendarEvent();
		if (!calendarEvent.getStart().equals(event.getNewStart()))
		{

			Period difference = Period.fieldDifference(
					LocalDateTime.fromDateFields(calendarEvent.getStart()),
					LocalDateTime.fromDateFields(calendarEvent.getEnd()));

			Date newEnd = LocalDateTime.fromDateFields(event.getNewStart())
					.plus(difference).toDate();

			reschedule(event.getCalendarEvent(), event.getNewStart(), newEnd,
					event.getComponent());
		}
	}

	public void addListener(CalendarHandlerListener listener)
	{
		if (!listeners.contains(listener))
		{
			listeners.add(listener);
		}
	}

	private void reschedule(CalendarEvent event, Date newStart, Date newEnd,
			Calendar calendar)
	{
		if (isFuture(event.getStart(), event.getEnd(), newStart, newEnd))
		{
			UI.getCurrent().addWindow(
					new RescheduleModal(event, newStart, newEnd, calendar));
		}
		else
		{
			Notification
					.show("Abort",
							"The event has already occurred or is being scheduled to occur in the past.",
							Type.WARNING_MESSAGE);
			calendar.markAsDirty();
		}
	}

	private boolean isFuture(Date... dates)
	{
		LocalDateTime now = LocalDateTime.now();
		for (Date date : dates)
		{
			if (LocalDateTime.fromDateFields(date).isBefore(now))
			{
				return false;
			}
		}
		return true;
	}

	private void showEventCreatedMessage(CalendarEvent event)
	{
		String caption = event.getCaption() + " has been created.";
		String description = event.getStart() + " " + event.getEnd();
		Notification.show(caption, description, Type.TRAY_NOTIFICATION);
	}

	private void showEventUpdatedMessage(CalendarEvent event)
	{
		String caption = event.getCaption() + " has been updated.";
		String description = event.getStart() + " " + event.getEnd();
		Notification.show(caption, description, Type.TRAY_NOTIFICATION);
	}

	private void showEventRescheduledMessage(CalendarEvent event)
	{
		String caption = event.getCaption() + " has been rescheduled.";
		String description = event.getStart() + " " + event.getEnd();
		Notification.show(caption, description, Type.TRAY_NOTIFICATION);
	}

	private void showOperationCanceledMessage(CalendarEvent event)
	{
		Notification
				.show("The operation was canceled.", Type.TRAY_NOTIFICATION);
	}

	protected void init()
	{
		setCompositionRoot(makeCalendar(cursor));
	}

	private Calendar makeCalendar(Date cursor)
	{
		Calendar calendar = getCalendarInstance();
		Range<Date> range = type.getDateRange(cursor);
		calendar.setStartDate(range.getMinimum());
		calendar.setEndDate(range.getMaximum());
		return calendar;
	}

	private Calendar getCalendarInstance()
	{
		Calendar calendar = new Calendar(provider);
		calendar.setImmediate(true);
		calendar.setSizeFull();
		calendar.setHandler((DateClickHandler) this);
		calendar.setHandler((EventResizeHandler) this);
		calendar.setHandler((EventMoveHandler) this);
		calendar.setHandler((EventClickHandler) this);
		calendar.setHandler((RangeSelectHandler) this);
		return calendar;
	}

	class EventEditor extends Window implements ClickListener
	{
		private static final long serialVersionUID = -464672570279240167L;

		private final Calendar calendar;

		private final BeanFieldGroup<JPACalendarEvent> fieldGroup;

		public EventEditor(JPACalendarEvent event, final Calendar calendar)
		{
			this.calendar = calendar;
			addCloseListener(getCloseListener());

			fieldGroup = new BeanFieldGroup<JPACalendarEvent>(
					JPACalendarEvent.class);
			BeanItem<JPACalendarEvent> item = new BeanItem<JPACalendarEvent>(
					event);
			fieldGroup.setItemDataSource(item);

			setModal(true);
			setWidth("300px");

			AccountField accountField = new AccountField("Participants");
			fieldGroup.bind(accountField, "accounts");
			accountField.setWidth("160px");

			TextField caption = fieldGroup.buildAndBind("Title", "caption",
					TextField.class);
			caption.setWidth("160px");

			TextField description = fieldGroup.buildAndBind("Location",
					"description", TextField.class);
			description.setWidth("160px");

			DateField start = fieldGroup.buildAndBind("start time", "start",
					DateField.class);
			start.setResolution(Resolution.MINUTE);

			DateField end = fieldGroup.buildAndBind("end time", "end",
					DateField.class);
			end.setResolution(Resolution.MINUTE);

			Button submit = new Button("Submit", this);
			submit.setClickShortcut(KeyCode.ENTER);

			Button cancel = new Button("Cancel", new Button.ClickListener()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event)
				{
					showOperationCanceledMessage(fieldGroup.getItemDataSource()
							.getBean());
					calendar.markAsDirty();
					close();
				}
			});
			cancel.setClickShortcut(KeyCode.ESCAPE);

			//@formatter:off
			FormLayout layout = new FormLayout(
					caption,
					description,
					start,
					end,
					accountField,
					new HorizontalLayout(submit,cancel));
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
				JPACalendarEvent calendarEvent = fieldGroup.getItemDataSource()
						.getBean();
				if (!((BasicEventProvider) calendar.getEventProvider())
						.containsEvent(calendarEvent))
				{
					calendar.addEvent(fieldGroup.getItemDataSource().getBean());
					showEventCreatedMessage(calendarEvent);
				}
				else
				{
					showEventUpdatedMessage(calendarEvent);
				}
				close();
			}
			catch (CommitException e)
			{
				e.printStackTrace();
			}
		}
	}

	class RescheduleModal extends Window implements Button.ClickListener
	{
		private static final long serialVersionUID = -6792649112010141742L;

		private final Date newStart;
		private final Date newEnd;
		private final CalendarEvent event;

		public RescheduleModal(final CalendarEvent event, Date newStart,
				Date newEnd, final Calendar calendar)
		{
			this.newStart = newStart;
			this.newEnd = newEnd;
			this.event = event;

			addCloseListener(getCloseListener());

			setWindowMode(WindowMode.NORMAL);
			setModal(true);
			setSizeUndefined();
			setCaption("Caution");
			addStyleName(Reindeer.WINDOW_LIGHT);

			BeanItemContainer<CalendarEvent> container = new BeanItemContainer<CalendarEvent>(
					CalendarEvent.class);
			container.addBean(new BasicEvent("FROM", "", event.getStart(),
					event.getEnd()));
			container.addBean(new BasicEvent("TO", "", newStart, newEnd));

			Table table = new Table();
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "caption", "start", "end" });
			table.setColumnHeaders(new String[] { "caption", "start", "end" });
			table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);

			setContent(new VerticalLayout(new Label(
					"Are you sure you want to reschedule " + event.getCaption()
							+ "?"), table, new HorizontalLayout(new Button(
					"Submit", this), new Button("Cancel",
					new Button.ClickListener()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent e)
						{
							close();
							showOperationCanceledMessage(event);
						}
					}))));
		}

		@Override
		public void buttonClick(ClickEvent event)
		{
			EditableCalendarEvent editableEvent = (EditableCalendarEvent) this.event;
			editableEvent.setStart(newStart);
			editableEvent.setEnd(newEnd);
			showEventRescheduledMessage(editableEvent);
			close();
		}
	}

	public CloseListener getCloseListener()
	{
		return this;
	}

	@Override
	public void windowClose(CloseEvent e)
	{
		init();
	}

	interface CalendarHandlerListener
	{
		public void onCursorSet(Date cursor);
	}
}