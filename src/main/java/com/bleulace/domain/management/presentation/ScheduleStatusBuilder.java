package com.bleulace.domain.management.presentation;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.Range;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.ctx.SpringApplicationContext;

class ScheduleStatusBuilder
{

	private static final Logger LOG = Logger
			.getLogger(ScheduleStatusBuilder.class);

	private final Account account;
	private final Range<LocalDate> range;
	private final TimeZone tz;
	private final Map<Range<Date>, RsvpStatus> eventMap = new HashMap<Range<Date>, RsvpStatus>();

	ScheduleStatusBuilder(String accountId, Date start, Date end, TimeZone tz)
	{
		this.account = EntityManagerReference.load(Account.class, accountId);
		this.tz = tz;
		this.range = Range.between(toLocalDate(start), toLocalDate(end),
				LocalDateComparator.INSTANCE);
		for (Event event : SpringApplicationContext.getBean(EventDAO.class)
				.findEvents(
						range.getMinimum().toDateMidnight().toDate(),
						range.getMaximum().toDateMidnight().plusDays(1)
								.toDate(), accountId))
		{
			addToMap(event);
		}
	}

	Map<LocalTime, ScheduleStatus> build()
	{
		return convertTimeMap(writeTimeMap());
	}

	private Map<LocalTime, ScheduleStatus> convertTimeMap(
			Map<LocalTime, Set<RsvpStatus>> map)
	{
		Map<LocalTime, ScheduleStatus> mergedMap = new HashMap<LocalTime, ScheduleStatus>();
		for (Entry<LocalTime, Set<RsvpStatus>> entry : map.entrySet())
		{
			mergedMap.put(entry.getKey(),
					ScheduleStatus.getStatus(entry.getValue()));
		}
		return mergedMap;
	}

	private Map<LocalTime, Set<RsvpStatus>> writeTimeMap()
	{
		LocalDateTime start = range.getMinimum().toLocalDateTime(
				LocalTime.MIDNIGHT);
		LocalDateTime end = range.getMaximum()
				.toLocalDateTime(LocalTime.MIDNIGHT).plusDays(1);

		Map<LocalTime, Set<RsvpStatus>> map = new HashMap<LocalTime, Set<RsvpStatus>>();
		for (LocalDateTime i = start.plusMillis(0); !i.isAfter(end); i = i
				.plusHours(1))
		{
			LocalTime key = i.toLocalTime();
			if (map.get(key) == null)
			{
				map.put(key, new HashSet<RsvpStatus>());
			}
			map.get(key).addAll(queryMap(i));
		}
		return map;
	}

	private Set<RsvpStatus> queryMap(LocalDateTime instant)
	{
		Set<RsvpStatus> statuses = new HashSet<RsvpStatus>();
		for (Entry<Range<Date>, RsvpStatus> entry : eventMap.entrySet())
		{
			if (entry.getKey().contains(instant.toDate()))
			{
				statuses.add(entry.getValue());
			}
		}
		return statuses;
	}

	private void addToMap(Event event)
	{
		Range<Date> key = Range.between(event.getWindow().getStart(), event
				.getWindow().getEnd());
		RsvpStatus value = event.getInvitees().get(account).getStatus();
		eventMap.put(key, value);
	}

	private Date toDate(LocalDate date, LocalTime time)
	{
		return date.toDateTime(time, getDateTimeZone()).toDate();
	}

	private LocalDateTime toLocalDateTime(Date date)
	{
		return new LocalDateTime(date.getTime(), getDateTimeZone());
	}

	private LocalDate toLocalDate(Date date)
	{
		return toLocalDateTime(date).toLocalDate();
	}

	private DateTimeZone getDateTimeZone()
	{
		return DateTimeZone.forTimeZone(tz);
	}

	private static class LocalDateComparator implements Comparator<LocalDate>
	{
		private static final Comparator<LocalDate> INSTANCE = new LocalDateComparator();

		private LocalDateComparator()
		{
		}

		@Override
		public int compare(LocalDate o1, LocalDate o2)
		{
			return o1.compareTo(o2);
		}
	}
}