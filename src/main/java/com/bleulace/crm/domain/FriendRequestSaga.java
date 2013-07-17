package com.bleulace.crm.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.repository.Repository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.axonframework.unitofwork.CurrentUnitOfWork;
import org.axonframework.unitofwork.SaveAggregateCallback;
import org.axonframework.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.crm.application.event.FriendRequestSentEvent;
import com.bleulace.crm.application.event.RepliedToFriendRequestEvent;
import com.bleulace.persistence.infrastructure.QueryFactory;

public class FriendRequestSaga extends AbstractAnnotatedSaga implements
		SaveAggregateCallback<Account>
{
	private static final long serialVersionUID = -6026833967810401361L;

	@Autowired
	private transient Repository<Account> accountRepository;

	@PersistenceContext
	private transient EntityManager entityManager;

	@Autowired
	private EventBus eventBus;

	private String initiatorId;
	private String recipientId;

	public FriendRequestSaga()
	{
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "sagaId")
	public void on(FriendRequestSentEvent event)
	{
		associateWith("sagaId", event.getSagaId());

		initiatorId = event.getInitiatorId();
		recipientId = event.getRecipientId();

		entityManager.persist(new FriendRequest(entityManager.getReference(
				Account.class, initiatorId), entityManager.getReference(
				Account.class, recipientId)));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "sagaId")
	public void on(RepliedToFriendRequestEvent event)
	{
		deleteRequest();
		if (event.isAccepted())
		{
			UnitOfWork uow = CurrentUnitOfWork.get();
			if (!uow.isStarted())
			{
				uow.start();
			}

			Account initiator = accountRepository.load(initiatorId);
			Account acceptor = accountRepository.load(recipientId);

			uow.registerAggregate(initiator, eventBus, this);
			uow.registerAggregate(acceptor, eventBus, this);

			initiator.getFriends().add(acceptor);
			acceptor.getFriends().add(initiator);

			uow.commit();
		}
		end();
	}

	@Transactional
	private void deleteRequest()
	{
		QFriendRequest r = QFriendRequest.friendRequest;
		FriendRequest request = QueryFactory
				.from(r)
				.where(r.initiator.id.eq(initiatorId).and(
						r.acceptor.id.eq(recipientId))).uniqueResult(r);
		entityManager.remove(request);
	}

	@Override
	@Transactional
	public void save(Account aggregate)
	{
		entityManager.merge(aggregate);
	}
}