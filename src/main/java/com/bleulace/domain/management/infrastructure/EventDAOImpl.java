package com.bleulace.domain.management.infrastructure;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.util.Assert;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.QEventParticipant;
import com.bleulace.domain.management.model.QPersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.jpa.config.QueryFactory;
import com.mysema.query.jpa.impl.JPAQuery;

class EventDAOImpl implements EventDAOCustom
{
	QPersistentEvent e = new QPersistentEvent("e");
	QEventParticipant i = new QEventParticipant("i");

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PersistentEvent> findEvents(Date start, Date end,
			String accountId)
	{
		return dateQuery(start, end).innerJoin(e.invitees, i)
				.where(i.account.id.eq(accountId)).list(e);
	}

	@Override
	public RsvpStatus findStatus(String eventId, String accountId)
	{
		return QueryFactory.from(e).innerJoin(e.invitees, i)
				.where(e.id.eq(eventId).and(i.account.id.eq(accountId)))
				.uniqueResult(i.status);
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
	public List<Account> findParticipants(PersistentEvent event)
	{
		return QueryFactory.from(e).innerJoin(e.invitees, i).where(e.eq(event))
				.list(i.account);
	}
}