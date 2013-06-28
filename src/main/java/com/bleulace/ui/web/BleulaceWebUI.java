package com.bleulace.ui.web;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.ui.web.calendar.CalendarView;
import com.bleulace.ui.web.front.FrontView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Configurable
@Widgetset("com.vaadin.DefaultWidgetSet")
@Theme("bleulacetheme")
public class BleulaceWebUI extends UI
{
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		setNavigator(new Navigator(this, this));
		getNavigator().addView(FrontView.NAME, FrontView.VIEW);
		getNavigator().addView(CalendarView.NAME, CalendarView.class);

		String viewName = FrontView.NAME;
		if (SecurityUtils.getSubject().isAuthenticated())
		{
			viewName = CalendarView.NAME;
		}
		getNavigator().navigateTo(viewName);
	}
}