package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.AssignmentEvent;
import com.bleulace.utils.jpa.EntityManagerReference;

aspect AssignableAspect
{
	@Enumerated(EnumType.STRING)
	@MapKeyJoinColumn
	@ManyToMany(cascade = CascadeType.ALL)
	private Map<Account, T> Assignable.Mixin<T>.assignees = new HashMap<Account, T>();

	public Map<Account, T> Assignable.Mixin<T>.getAssignees()
	{
		return this.assignees;
	}

	@EventHandler
	public void Assignable.Mixin<T>.on(AssignmentEvent<T> event)
	{
		if (this.getId().equals(event.getId()))
		{
			this.assignees.put(
					EntityManagerReference.get().getReference(Account.class,
							event.getAccountId()), event.getRole());
		}
	}
}