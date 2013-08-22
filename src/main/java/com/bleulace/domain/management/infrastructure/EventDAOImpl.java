package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
				.distinct()
				.innerJoin(e.invitees, i)
				.where(i.guest.id.eq(accountId).and(
						i.status.ne(RsvpStatus.DECLINED))).list(e);
	}

	@Override
	public Set<RsvpStatus> findRsvps(Date start, Date end, String accountId)
	{
		return new HashSet<RsvpStatus>(dateQuery(start, end)
				.innerJoin(e.invitees, i)
				.where(i.guest.id.eq(accountId).and(
						i.status.ne(RsvpStatus.DECLINED))).list(i.status));
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

	@Override
	public List<Event> findEvents(Date instant, String accountId)
	{
		return findEvents(instant, instant, accountId);
	}
}