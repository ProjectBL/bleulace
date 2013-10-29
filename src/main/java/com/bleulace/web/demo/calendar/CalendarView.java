package com.bleulace.web.demo.calendar;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;

@VaadinView
class CalendarView extends CustomComponent implements View
{
	@Autowired
	private Calendar calendar;

	@PostConstruct
	protected void init()
	{
		setCompositionRoot(calendar);
	}

	@RequiresAuthentication
	@Override
	public void enter(ViewChangeEvent event)
	{
		SecurityUtils
				.getSubject()
				.getSession()
				.setAttribute("targetId",
						SpringApplicationContext.getUser().getId());
	}
}
