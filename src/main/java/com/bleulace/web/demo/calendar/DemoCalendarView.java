package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component(DemoCalendarView.VIEW_NAME)
class DemoCalendarView extends CustomComponent implements View
{
	public static final String VIEW_NAME = "calendarView";

	@Autowired
	private Layout leftLayout;

	@Autowired
	private Layout centerLayout;

	@Autowired
	private Layout rightLayout;

	@Override
	public void enter(ViewChangeEvent event)
	{
		if (getCompositionRoot() == null)
		{
			HorizontalLayout root = new HorizontalLayout(leftLayout,
					centerLayout);// , rightLayout);
			setCompositionRoot(root);
		}
	}
}