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
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Configurable(preConstruction = true)
class TimeBoxPresenter
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	private TimeBox view;

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

	void setView(TimeBox view)
	{
		this.view = view;
	}

	void cancelClicked()
	{
		view.showSuccessMessage("Operation canceled.");
		view.close();
	}

	void deleteClicked()
	{
		view.showWarningDialog("Are you sure you want to delete "
				+ getCurrentEvent().getCaption() + "?");
	}

	void warningAccepted(boolean accepted)
	{
		if (accepted)
		{
			calendar.removeEvent((CalendarEvent) ctx.getBean("calendarAdapter",
					getCurrentEvent()));
			view.showSuccessMessage("Event deleted.");
			view.close();
		}
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
			view.showSuccessMessage("Event "
					+ (event.getSource().isNew() ? "created successfully."
							: "updated successfully."));
			view.close();
		}
		catch (CommitException e)
		{
			view.showWarningMessage("Invalid timebox state.");
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