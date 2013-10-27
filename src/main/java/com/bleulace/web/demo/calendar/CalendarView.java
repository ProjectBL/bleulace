package com.bleulace.web.demo.calendar;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.web.demo.timebox.TimeBox;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Scope("ui")
@Component(CalendarView.VIEW_NAME)
class CalendarView extends CustomComponent implements View
{
	public static final String VIEW_NAME = "calendarView";

	@Autowired
	private Layout leftLayout;

	@Autowired
	private Layout centerLayout;

	@Autowired
	private TimeBox timeBox;

	@Override
	@RequiresUser
	public void enter(ViewChangeEvent event)
	{
		HorizontalLayout root = new HorizontalLayout(leftLayout, centerLayout);
		setCompositionRoot(root);
	}

	void showTimeBox(PersistentEvent event)
	{
		Assert.notNull(event);
		timeBox.show(event);
	}
}