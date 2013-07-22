package com.bleulace.mgt.domain;

import java.util.Date;
import java.util.List;

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
		QEvent e = QEvent.event;
		return QueryFactory
				.from(e)
				.where(e.window.start.before(end)
						.and(e.window.end.after(start))).list(e);

	}
}
