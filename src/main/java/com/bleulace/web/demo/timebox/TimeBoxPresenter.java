package com.bleulace.web.demo.timebox;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;

@Configurable(preConstruction = true)
class TimeBoxPresenter
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	private MessageBox view;

	private final Calendar calendar;

	private final BeanFieldGroup<CalendarEventAdapter> fieldGroup = makeFieldGroup();

	TimeBoxPresenter(CalendarEventAdapter event, Calendar calendar)
	{
		this.calendar = calendar;
		fieldGroup.setItemDataSource(event);
	}

	CalendarEventAdapter getCurrentEvent()
	{
		return fieldGroup.getItemDataSource().getBean();
	}

	BeanFieldGroup<CalendarEventAdapter> getFieldGroup()
	{
		return fieldGroup;
	}

	void setView(MessageBox view)
	{
		this.view = view;
	}

	void cancelClicked()
	{
		Notification.show("Operation canceled.", Type.TRAY_NOTIFICATION);
		view.close();
	}

	void deleteClicked()
	{
		MessageBox
				.showPlain(
						Icon.WARN,
						"Warning",
						"Are you sure you want to delete "
								+ fieldGroup.getItemDataSource().getBean()
										.getCaption() + "?", ButtonId.CANCEL,
						ButtonId.OK).getButton(ButtonId.OK)
				.addClickListener(new Button.ClickListener()
				{

					@Override
					public void buttonClick(ClickEvent event)
					{
						warningAccepted();
					}
				});
		;
	}

	void warningAccepted()
	{
		calendar.removeEvent((CalendarEvent) ctx.getBean("calendarAdapter",
				getCurrentEvent()));
		Notification.show("Event deleted.", Type.TRAY_NOTIFICATION);
		view.close();
	}

	@RequiresUser
	void applyClicked()
	{
		try
		{
			fieldGroup.commit();
			CalendarEventAdapter event = getCurrentEvent();
			if (event.getSource().isNew())
			{
				calendar.addEvent(event);
			}
			Notification.show("Event "
					+ (event.getSource().isNew() ? "created successfully."
							: "updated successfully."), Type.TRAY_NOTIFICATION);
			view.close();
		}
		catch (CommitException e)
		{
			Notification.show("Invalid timebox state.", Type.WARNING_MESSAGE);
		}
	}

	void timeBoxClosed()
	{
	}

	private BeanFieldGroup<CalendarEventAdapter> makeFieldGroup()
	{
		BeanFieldGroup<CalendarEventAdapter> bean = new BeanFieldGroup<CalendarEventAdapter>(
				CalendarEventAdapter.class);
		return bean;
	}
}