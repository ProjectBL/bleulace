package com.bleulace.web.demo.calendar.appearance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.web.BleulaceTheme.CalendarColor;
import com.bleulace.web.SystemUser;
import com.google.common.collect.ForwardingMap;

@Component
@Scope(value = "ui", proxyMode = ScopedProxyMode.TARGET_CLASS)
class MultipleVieweeStrategy extends ForwardingMap<String, CalendarColor>
		implements StyleNameCallback
{
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private SystemUser user;

	private final Map<String, CalendarColor> delegate = new HashMap<String, CalendarColor>();

	@Override
	public String evaluate(PersistentEvent source)
	{
		return null;
	}

	@Override
	protected Map<String, CalendarColor> delegate()
	{
		return delegate;
	}

}