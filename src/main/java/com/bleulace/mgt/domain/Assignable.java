package com.bleulace.mgt.domain;

import java.util.Map;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.AssignmentEvent;

public interface Assignable<T extends Enum<T>>
{
	public String getId();

	public Map<Account, ? extends T> getAssignees();

	public interface Mixin<T extends Enum<T>> extends Assignable<T>
	{
		public void on(AssignmentEvent<T> event);
	}
}