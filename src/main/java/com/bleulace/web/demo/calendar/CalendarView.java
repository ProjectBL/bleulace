package com.bleulace.web.demo.calendar;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@VaadinView
class CalendarView extends CustomComponent implements View
{
	@Autowired
	private TabSheet tabSheet;
	@Autowired
	private Calendar calendar;

	@PostConstruct
	protected void init()
	{
		setCompositionRoot(new VerticalLayout(tabSheet, calendar));
	}

	@RequiresAuthentication
	@Override
	public void enter(ViewChangeEvent event)
	{
		UI.getCurrent().addWindow(
				SpringApplicationContext.getBean(Window.class,
						"viewTargetWindow"));
	}
}
