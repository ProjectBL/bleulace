package com.bleulace.ui.web;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.JPACalendarEventProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Configurable
public class BleulaceWebUI extends UI
{
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		Account.login("arleighdickerson@frugalu.com", "password");

		// BasicEventProvider b = new BasicEventProvider();
		// BasicEvent e = new BasicEvent("foo", "bar",
		// DateTime.now().plusHours(1)
		// .toDate(), DateTime.now().plusHours(3).toDate());
		final JPACalendarEventProvider p = new JPACalendarEventProvider(
				Account.current());
		final Calendar c = new Calendar(p);
		c.setImmediate(true);
		c.setStartDate(LocalDate.now().toDate());
		c.setEndDate(LocalDate.now().plusWeeks(1).toDate());
		// c.addEvent(e);
		c.setHeight("800px");
		c.setWidth("1500px");
		setContent(new VerticalLayout(c, new Button("do it son",
				new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						p.flushChanges();
					}
				})));
	}
}