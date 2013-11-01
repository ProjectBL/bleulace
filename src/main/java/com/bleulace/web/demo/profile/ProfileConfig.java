package com.bleulace.web.demo.profile;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.IdCollectionQueryModifier;
import com.bleulace.utils.CallByName;
import com.bleulace.web.SystemUser;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

@Configuration
class ProfileConfig
{
	@Autowired
	private SystemUser user;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountDAO accountDAO;

	@Bean
	@Scope("prototype")
	public JPAContainer<Account> friendContainer()
	{
		JPAContainer<Account> container = JPAContainerFactory
				.makeNonCachedReadOnly(Account.class, em);
		container.setQueryModifierDelegate(new IdCollectionQueryModifier(
				new CallByName<Collection<String>>()
				{
					@Override
					public Collection<String> evaluate()
					{
						return accountDAO.findFriendIds(user.getId());
					}
				}));
		return container;
	}
}