package com.bleulace.domain.crm.model;

import org.axonframework.domain.MetaData;

import com.bleulace.domain.crm.infrastructure.WithUnitOfWork;

public enum FriendRequestAction implements FriendRequestActionOps
{
	REQUEST(new RequestOps()), ACCEPT(new AcceptOps()), DENY(new DenyOps()), CANCEL(
			new CancelOps()), REMOVE(new RemoveOps());

	private final FriendRequestActionOps ops;

	@Override
	public void execute(Account initiator, MetaData metaData)
	{
		ops.execute(initiator, metaData);
	}

	private FriendRequestAction(FriendRequestActionOps ops)
	{
		this.ops = ops;
	}

	static class RequestOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account target, MetaData metaData)
		{
			Account executor = metaData.getAccount();
			target.getFriendRequests().put(executor,
					new FriendRequest(executor));
		}
	}

	static class DenyOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account target, MetaData metaData)
		{
			Account executor = metaData.getAccount();
			executor.getFriendRequests().remove(target);
		}
	}

	static class AcceptOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account friendToAdd, MetaData metaData)
		{
			Account executor = metaData.getAccount();
			if (executor.getFriendRequests().containsKey(friendToAdd))
			{
				executor.getFriendRequests().remove(friendToAdd);
				executor.getFriends().add(friendToAdd);
				friendToAdd.getFriends().add(executor);
			}
		}
	}

	static class RemoveOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account friendToRemove, MetaData metaData)
		{
			Account executor = metaData.getAccount();
			WithUnitOfWork.register(friendToRemove);
			executor.getFriends().remove(friendToRemove);
			friendToRemove.getFriends().remove(executor);
		}
	}

	static class CancelOps implements FriendRequestActionOps
	{
		@Override
		public void execute(Account toCancel, MetaData metaData)
		{
			toCancel.getFriendRequests().remove(metaData.getAccount());
		}
	}
}