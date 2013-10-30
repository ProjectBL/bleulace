package com.bleulace.domain.management.infrastructure;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.QEventInvitee;
import com.bleulace.domain.management.model.QPersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.jpa.config.QueryFactory;
import com.mysema.query.jpa.impl.JPAQuery;

class EventDAOImpl implements EventDAOCustom
{
	private final QPersistentEvent e = new QPersistentEvent("e");
	private final QEventInvitee i = new QEventInvitee("i");

	@Override
	public List<PersistentEvent> findEvents(Date start, Date end,
			String accountId)
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
		return QueryFactory.from(e)
				.where(e.start.before(end).and(e.end.after(start))).distinct()
				.orderBy(e.start.asc());
	}

	@Override
	public List<PersistentEvent> findEvents(Date instant, String accountId)
	{
		QueryFactory.from(e).createQuery(e);
		return findEvents(instant, instant, accountId);
	}

	@Override
	public List<PersistentEvent> findEvents(String accountId)
	{
		Assert.notNull(accountId);
		return QueryFactory.from(e).distinct().innerJoin(e.invitees, i)
				.where(i.guest.id.eq(accountId)// .and(i.status.ne(RsvpStatus.DECLINED))
				).list(e);
	}

	@Override
	public List<PersistentEvent> findEvents(Date start, Date end,
			Collection<String> accountIds)
	{
		Assert.notNull(accountIds);
		return dateQuery(start, end).join(e.invitees, i).fetch()
				.where(i.guest.id.in(accountIds)).distinct()
				.orderBy(e.start.asc()).list(e);
	}

	@Override
	public boolean exists(Date start, Date end, Collection<String> accountIds)
	{
		return dateQuery(start, end).innerJoin(e.invitees, i)
				.where(i.guest.id.in(accountIds)).exists();
	}

	@Override
	public List<PersistentEvent> findEvents(Date start, Date end,
			Collection<String> accountIds, Collection<String> cachedEventIds)
	{
		return dateQuery(start, end)
				.innerJoin(e.invitees, i)
				.where(i.guest.id.in(accountIds)
						.and(e.id.notIn(cachedEventIds))).list(e);
	}
}