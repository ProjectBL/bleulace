package com.bleulace.mgt.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.GuestInvitedEvent;

public class EventInvitationSaga extends AbstractAnnotatedSaga
{
	private static final long serialVersionUID = -2035609951731974621L;

	@PersistenceContext
	private EntityManager em;

	public EventInvitationSaga()
	{
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "sagaId")
	public void on(GuestInvitedEvent event)
	{
		if (event.getClass().equals(GuestInvitedEvent.class))
		{
			associateWith("sagaId", event.getSagaId());
			Account account = em
					.getReference(Account.class, event.getGuestId());
			Event target = em.getReference(Event.class, event.getId());
			em.persist(new EventInvitation(account, target));
		}
		else
		{
			EventInvitation.delete(event.getGuestId(), event.getId());
			end();
		}
	}
}
