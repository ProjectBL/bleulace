package com.bleulace.web.demo.profile;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.jpa.IdCollectionQueryModifier;
import com.bleulace.utils.CallByName;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.Presenter;
import com.vaadin.addon.jpacontainer.JPAContainer;

@Presenter
class ProfilePresenter
{
	private Account account;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ProfileView view;

	@Autowired
	private SystemUser user;

	void init(String accountId)
	{
		account = accountDAO.findOne(accountId);
		if (account == null)
		{
			throw new IllegalArgumentException();
		}
		setContainerFilters();
	}

	private void setContainerFilters()
	{
		JPAContainer<?>[] containers = new JPAContainer<?>[] {
				view.getProjects(), view.getEvents() };
		for (JPAContainer<?> container : containers)
		{
			container.removeAllContainerFilters();
		}

		view.getProjects().addContainerFilter("assignments.account.id",
				account.getId(), false, false);
		view.getEvents().setQueryModifierDelegate(
				new IdCollectionQueryModifier(
						new CallByName<Collection<String>>()
						{
							@Override
							public Collection<String> evaluate()
							{
								return eventDAO.findEventIds(user.getId());
							}
						}));
	}
}