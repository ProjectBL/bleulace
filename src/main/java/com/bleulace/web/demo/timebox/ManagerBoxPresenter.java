package com.bleulace.web.demo.timebox;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.vaadin.data.util.BeanContainer;

@Configurable(preConstruction = true)
class ManagerBoxPresenter
{
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	private ManagerBox view;

	private final AbstractResource resource;

	ManagerBoxPresenter(AbstractResource resource)
	{
		this.resource = resource;
	}

	void setView(ManagerBox managerBox)
	{
		this.view = managerBox;
		refreshCandidates();
		refreshManagers();
	}

	@SuppressWarnings("unchecked")
	void managerUpdated(String id, ManagementLevel level)
	{
		if (level == null)
		{
			view.getManagers().removeItem(id);
		}
		else
		{
			view.getManagers().getItem(id).getItemProperty("level")
					.setValue(level);
		}
	}

	void managerAdded(Account account)
	{
		Assert.notNull(account);
		ManagerBean bean = new ManagerBean(account);
		BeanContainer<String, ManagerBean> container = view.getManagers();
		container.addBean(bean);
	}

	void submitClicked()
	{
		List<String> managerIds = new ArrayList<String>();
		for (String id : view.getManagers().getItemIds())
		{
			ManagementLevel level = view.getManagers().getItem(id).getBean()
					.getLevel();
			resource.setManagementLevel(id, level);
			managerIds.add(id);
		}
		for (Object id : view.getCandidates().getItemIds())
		{
			if (!managerIds.contains(id))
			{
				resource.setManagementLevel((String) id, null);
			}
		}
		view.close();
	}

	void cancelClicked()
	{
		view.close();
	}

	private void refreshManagers()
	{
		view.getManagers().removeAllItems();
		for (ManagementAssignment assignment : resource.getAssignments())
		{
			view.getManagers().addBean(
					new ManagerBean(assignment.getAccount(), assignment
							.getRole()));
		}
	}

	private void refreshCandidates()
	{
		view.getCandidates().removeAllItems();
	}
}
