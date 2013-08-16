package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.QEvent;
import com.bleulace.domain.management.model.QEventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.jpa.config.QueryFactory;
import com.mysema.query.jpa.impl.JPAQuery;

class EventDAOImpl implements EventDAOCustom
{
	private final QEvent e = new QEvent("e");
	private final QEventInvitee i = new QEventInvitee("i");

	@Override
	public List<Event> findEvents(Date start, Date end, String accountId)
	{
		Assert.notNull(accountId);
		return dateQuery(start, end)
				.innerJoin(e.invitees, i)
				.where(i.guest.id.eq(accountId).and(
						i.status.ne(RsvpStatus.DECLINED))).list(e);
	}

	private JPAQuery dateQuery(Date start, Date end)
	{
		Assert.notNull(start);
		Assert.notNull(end);
		return QueryFactory
				.from(e)
				.where(e.window.start.before(end)
						.and(e.window.end.after(start)))
				.orderBy(e.window.start.asc());
	}
}
