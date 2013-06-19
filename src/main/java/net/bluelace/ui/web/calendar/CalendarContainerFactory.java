package net.bluelace.ui.web.calendar;

import java.util.Date;

import net.bluelace.domain.account.Account;
import net.bluelace.domain.calendar.BLContainerEventProvider;
import net.bluelace.ui.web.calendar.CalendarView.TabDescriptor;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.vaadin.ui.Calendar;

public class CalendarContainerFactory
{
	public static CalendarContainer make(Account account,
			TabDescriptor descriptor)
	{
		Calendar calendar = new Calendar(new BLContainerEventProvider(account));
		return null;
	}

	public static Date getStartDate(TabDescriptor descriptor)
	{
		switch (descriptor)
		{
		case MONTH:
			return startOfMonth();
		case WEEK:
			return startOfWeek();
		default:
			return new Date();
		}
	}

	public static Date getEndDate(TabDescriptor descriptor)
	{
		switch (descriptor)
		{
		case MONTH:
			return endOfMonth();
		case WEEK:
			return endOfWeek();
		default:
			return new Date();
		}
	}

	private static Date startOfMonth()
	{
		LocalDate today = LocalDate.now();
		return new LocalDate(today.getYear(), today.getMonthOfYear(), 1)
				.toDateMidnight().toDate();
	}

	private static Date endOfMonth()
	{
		LocalDate today = LocalDate.now();
		LocalDate value = LocalDate.now();
		while (value.monthOfYear().equals(today.monthOfYear()))
		{
			value = value.plusDays(1);
		}
		return value.toDateMidnight().toDate();
	}

	private static Date startOfWeek()
	{
		LocalDate value = LocalDate.now();
		while (value.getDayOfWeek() != DateTimeConstants.SUNDAY)
		{
			value = value.minusDays(1);
		}
		return value.toDateMidnight().toDate();
	}

	private static Date endOfWeek()
	{
		LocalDate value = LocalDate.now();
		while (value.getDayOfWeek() != DateTimeConstants.SATURDAY)
		{
			value = value.plusDays(1);
		}
		return value.toDateMidnight().toDate();
	}
}
