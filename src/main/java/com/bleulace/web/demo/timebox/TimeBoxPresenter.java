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
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
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

	void managersClicked()
	{
		Window w = (Window) ctx.getBean("managerBox", getCurrentEvent()
				.getSource());
		UI.getCurrent().addWindow(w);
	}

	void cancelClicked()
	{
		view.showSuccessMessage("Operation canceled.");
		view.close();
	}

	void deleteClicked()
	{
		calendar.removeEvent((CalendarEvent) ctx.getBean("calendarAdapter",
				getCurrentEvent()));
		view.showSuccessMessage("Event deleted.");
		view.close();
	}

	@RequiresUser
	void applyClicked()
	{
		try
		{
			fieldGroup.commit();
			CalendarEventAdapter event = getCurrentEvent();
			view.showSuccessMessage("Event "
					+ (event.getSource().isNew() ? "created successfully."
							: "updated successfully."));
			calendar.addEvent(event);
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