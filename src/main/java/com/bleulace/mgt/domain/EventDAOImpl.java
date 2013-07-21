package com.bleulace.mgt.domain;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import com.bleulace.persistence.infrastructure.QueryFactory;
import com.bleulace.utils.ctx.SpringApplicationContext;

class EventDAOImpl implements EventDAOCustom
{
	@Override
	public List<Event> findByAssignment(String accountId,
			ManagementAssignment assignment)
	{
		return SpringApplicationContext.getBean(ProjectDAO.class)
				.findByAssignment(accountId, assignment, Event.class);
	}

	@Override
	public List<Event> findByAssignment(String accountId)
	{
		return SpringApplicationContext.getBean(ProjectDAO.class)
				.findByAssignment(accountId, Event.class);
	}

	@Override
	public List<Event> findBetweenDates(Date start, Date end)
	{
		LocalDateTime startDT = LocalDateTime.fromDateFields(start);
		LocalDateTime endDT = LocalDateTime.fromDateFields(end);
		Period period = Period.fieldDifference(startDT, endDT);
		QEvent e = QEvent.event;
		return QueryFactory
				.from(e)
				.where(e.start.between(startDT, endDT)
						.and(e.length.loe(period))).list(e);

	}
}
