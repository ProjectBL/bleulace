package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import org.axonframework.eventhandling.annotation.EventHandler;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;
import com.bleulace.persistence.utils.EntityManagerReference;

public interface ManageableMixin extends Manageable
{
	static aspect Impl
	{
		public Map<Account, ManagementLevel> ManageableMixin.getManagers()
		{
			// TODO : implement me!
			Map<Account, ManagementLevel> managers = new HashMap<Account, ManagementLevel>();
			return managers;
		}
	}
}