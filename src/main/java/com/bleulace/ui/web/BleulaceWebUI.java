package com.bleulace.ui.web;

import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.CalendarComposite;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Configurable
public class BleulaceWebUI extends UI
{
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		Account.login("arleighdickerson@frugalu.com", "password");
		setContent(new CalendarComposite());
	}
}