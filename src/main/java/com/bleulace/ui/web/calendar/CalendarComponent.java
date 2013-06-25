package com.bleulace.ui.web.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.CalendarHandler.CalendarHandlerListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;

class CalendarComponent extends CustomComponent implements
		CalendarHandlerListener
{
	private static final long serialVersionUID = -1466918535763058564L;

	private final Map<CalendarType, Tab> tabs = new HashMap<CalendarType, Tab>();

	private final TabSheet tabSheet = new TabSheet();

	public CalendarComponent()
	{
		JPACalendarEventProvider provider = new JPACalendarEventProvider(
				new AccountCalendarEventProvider(Account.current()));

		for (CalendarType type : CalendarType.values())
		{
			CalendarHandler handler = new CalendarHandler(provider, type);
			handler.addListener(this);

			Tab tab = tabSheet.addTab(handler);
			tab.setCaption(type.toString());
			tabs.put(type, tab);
		}

		setCompositionRoot(tabSheet);
	}

	@Override
	public void onCursorSet(Date cursor)
	{
		for (CalendarType key : tabs.keySet())
		{
			((CalendarHandler) tabs.get(key).getComponent()).setCursor(cursor);
		}
		tabSheet.setSelectedTab(tabs.get(CalendarType.DAY));
	}
}