package com.bleulace.mgt.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;

import com.bleulace.crm.domain.Account;

public interface AssignableMixin extends Assignable
{
	static aspect Impl
	{
		@ManyToMany
		private Set<Account> AssignableMixin.assignees = new HashSet<Account>();

		public Set<Account> AssignableMixin.getAssignees()
		{
			return assignees;
		}

		private void AssignableMixin.assign(Account... accounts)
		{
			assignees.addAll(Arrays.asList(accounts));
		}

		private void AssignableMixin.unAssign(Account... accounts)
		{
			assignees.removeAll(Arrays.asList(accounts));
		}
	}
}
