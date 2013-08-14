package com.bleulace.domain.crm.model;

import com.bleulace.jpa.EntityManagerReference;

public enum GroupMembershipAction implements GroupMembershipActionOps
{
	JOIN(new JoinOps()), LEAVE(new LeaveOps());

	private final GroupMembershipActionOps ops;

	private GroupMembershipAction(GroupMembershipActionOps ops)
	{
		this.ops = ops;
	}

	@Override
	public void execute(AccountGroup group, String accountId)
	{
		ops.execute(group, accountId);
	}

	static class JoinOps implements GroupMembershipActionOps
	{
		@Override
		public void execute(AccountGroup group, String accountId)
		{
			group.getMembers().add(getAccount(accountId));
		}
	}

	static class LeaveOps implements GroupMembershipActionOps
	{
		@Override
		public void execute(AccountGroup group, String accountId)
		{
			group.getMembers().remove(getAccount(accountId));
		}
	}

	private static final Account getAccount(String accountId)
	{
		return EntityManagerReference.load(Account.class, accountId);
	}
}
