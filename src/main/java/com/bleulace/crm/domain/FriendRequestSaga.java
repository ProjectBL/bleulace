package com.bleulace.crm.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.repository.Repository;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.crm.application.event.FriendRequestSentEvent;
import com.bleulace.crm.application.event.RepliedToFriendRequestEvent;
import com.bleulace.persistence.infrastructure.QueryFactory;

@Configurable
public class FriendRequestSaga extends AbstractAnnotatedSaga
{
	private static final long serialVersionUID = -6026833967810401361L;

	@Autowired
	private transient Repository<Account> accountRepository;

	@PersistenceContext
	private transient EntityManager entityManager;

	private String initiatorId;
	private String acceptorId;

	public FriendRequestSaga()
	{
	}

	@StartSaga
	@SagaEventHandler(associationProperty = "sagaId")
	@Transactional
	public void on(FriendRequestSentEvent event)
	{
		initiatorId = event.getInitiatorId();
		acceptorId = event.getAcceptorId();

		entityManager.persist(new FriendRequest(entityManager.getReference(
				Account.class, initiatorId), entityManager.getReference(
				Account.class, acceptorId)));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "sagaId")
	public void on(RepliedToFriendRequestEvent event)
	{
		if (event.isAccepted())
		{
			Account initiator = getRequest().getInitiator();
			Account acceptor = getRequest().getAcceptor();
			initiator.addFriend(acceptor);
		}
		end();
	}

	public String getSagaId()
	{
		return initiatorId + ":" + acceptorId;
	}

	private FriendRequest getRequest()
	{
		QFriendRequest r = QFriendRequest.friendRequest;
		return QueryFactory
				.from(r)
				.where(r.initiator.id.eq(initiatorId).and(
						r.acceptor.id.eq(acceptorId))).uniqueResult(r);
	}
}