package com.bleulace.web.demo.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.Presenter;
import com.vaadin.data.util.BeanContainer;

@Presenter
class ManagerBoxPresenter
{
	@Autowired
	@Qualifier("managementParticipants")
	private BeanContainer<String, ManagerBean> managers;

	@Autowired
	@Qualifier("managementCandidates")
	private BeanContainer<String, ManagerBean> candidates;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private ManagerBox managerBox;

	private AbstractResource resource;

	void setCurrentResource(AbstractResource resource)
	{
		Assert.notNull(resource);
		this.resource = resource;

		refreshCandidates();
		refreshManagers();
	}

	@SuppressWarnings("unchecked")
	void managerUpdated(String id, ManagementLevel level)
	{
		if (level == null)
		{
			managers.removeItem(id);
		}
		else
		{
			managers.getItem(id).getItemProperty("level").setValue(level);
		}
	}

	void managerAdded(String id)
	{
		ManagerBean bean = candidates.getItem(id).getBean();
		managers.addBean(bean);
	}

	void submitClicked()
	{
		List<String> managerIds = new ArrayList<String>();
		for (String id : managers.getItemIds())
		{
			ManagementLevel level = managers.getItem(id).getBean().getLevel();
			resource.setManagementLevel(id, level);
			managerIds.add(id);
		}
		for (String id : candidates.getItemIds())
		{
			if (!managerIds.contains(id))
			{
				resource.setManagementLevel(id, null);
			}
		}
		managerBox.close();
	}

	private void refreshManagers()
	{
		managers.removeAllItems();
		for (ManagementAssignment assignment : resource.getAssignments())
		{
			managers.addBean(new ManagerBean(assignment.getAccount(),
					assignment.getRole()));
		}
	}

	private void refreshCandidates()
	{
		candidates.removeAllItems();
		Set<Account> friends = accountDAO.findOne(
				SpringApplicationContext.getUser().getId()).getFriends();
		for (Account friend : friends)
		{
			candidates.addBean(new ManagerBean(friend));
		}
	}
}
