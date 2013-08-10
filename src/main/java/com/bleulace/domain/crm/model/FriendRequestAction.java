package com.bleulace.domain.crm.model;

import javax.persistence.EntityNotFoundException;

import com.bleulace.domain.crm.infrastructure.FriendRequest;
import com.bleulace.domain.crm.infrastructure.QFriendRequest;
import com.bleulace.jpa.infrastructure.QueryFactory;
import com.bleulace.utils.jpa.EntityManagerReference;
import com.mysema.query.jpa.impl.JPAQuery;

public enum FriendRequestAction implements FriendRequestActionOps
{
	REQUEST(new RequestOps()), ACCEPT(new AcceptOps()), DENY(new DenyOps()), CANCEL(
			new CancelOps()), REMOVE(new RemoveOps());

	private final FriendRequestActionOps ops;

	@Override
	public void execute(Account initiator, Account recipient)
	{
		ops.execute(initiator, recipient);
	}

	private FriendRequestAction(FriendRequestActionOps ops)
	{
		this.ops = ops;
	}

	static class RequestOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account initiator, Account recipient)
		{
			EntityManagerReference.get().persist(
					new FriendRequest(initiator, recipient));
		}
	}

	static class AcceptOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account initiator, Account recipient)
		{
			delete(recipient, initiator);
			initiator.getFriends().add(recipient);
			recipient.getFriends().add(initiator);
		}
	}

	static class DenyOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account initiator, Account recipient)
		{
			delete(recipient, initiator);
		}
	}

	static class CancelOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account initiator, Account recipient)
		{
			delete(initiator, recipient);
		}
	}

	static class RemoveOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account initiator, Account recipient)
		{
			initiator.getFriends().remove(recipient);
			recipient.getFriends().remove(initiator);
		}
	}

	private static final QFriendRequest R = QFriendRequest.friendRequest;

	private static FriendRequest find(Account initiator, Account recipient)
	{
		FriendRequest r = query(initiator, recipient).singleResult(R);
		if (r == null)
		{
			throw new EntityNotFoundException();
		}
		return r;
	}

	private static void delete(Account initiator, Account recipient)
	{
		if (QueryFactory.delete(R)
				.where(R.initiator.eq(initiator).and(R.acceptor.eq(recipient)))
				.execute() == 0)
		{
			throw new EntityNotFoundException();
		}
	}

	private static JPAQuery query(Account initiator, Account recipient)
	{
		return QueryFactory.from(R).where(
				R.initiator.eq(recipient).and(R.acceptor.eq(initiator)));
	}
}