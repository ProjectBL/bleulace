package com.bleulace.ui.web.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bleulace.domain.account.Account;
import com.bleulace.ui.web.calendar.CalendarHandler.CalendarHandlerListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;

public class CalendarComposite extends CustomComponent implements
		CalendarHandlerListener
{
	private static final long serialVersionUID = -1466918535763058564L;

	private final Map<CalendarType, CalendarHandler> handlers = new HashMap<CalendarType, CalendarHandler>();

	public CalendarComposite()
	{
		JPACalendarEventProvider provider = new JPACalendarEventProvider(
				Account.current());

		TabSheet tabSheet = new TabSheet();

		for (CalendarType type : CalendarType.values())
		{
			CalendarHandler handler = new CalendarHandler(provider, type);

			handler.addListener(this);
			handlers.put(type, handler);

			Tab tab = tabSheet.addTab(handler);
			tab.setCaption(type.toString());
		}

		setCompositionRoot(tabSheet);
	}

	@Override
	public void onCursorSet(Date cursor)
	{
		for (CalendarType key : handlers.keySet())
		{
			handlers.get(key).setCursor(cursor);
		}
	}
}