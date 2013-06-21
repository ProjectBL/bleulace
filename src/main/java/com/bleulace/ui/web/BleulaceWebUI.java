package com.bleulace.ui.web;

import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.CalendarViewImpl;
import com.bleulace.ui.web.front.FrontViewImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Configurable
public class BleulaceWebUI extends UI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191009197124553972L;

	@Override
	protected void init(VaadinRequest request)
	{
		Account.login("arleighdickerson@frugalu.com", "password");
		Navigator navigator = new Navigator(this, this);
		navigator.addView("front", FrontViewImpl.class);
		navigator.addView("calendar", CalendarViewImpl.class);
		navigator.navigateTo("calendar");
	}
}