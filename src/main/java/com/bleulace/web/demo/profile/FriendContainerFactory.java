package com.bleulace.web.demo.profile;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.IdCollectionQueryModifier;
import com.bleulace.utils.IdsCallback;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

@Component("friendContainer")
@Scope("prototype")
class FriendContainerFactory implements FactoryBean<JPAContainer<Account>>
{
	private final String id;

	@Autowired
	private AccountDAO accountDAO;

	@PersistenceContext
	private EntityManager em;

	FriendContainerFactory()
	{
		this(null);
	}

	FriendContainerFactory(String id)
	{
		this.id = id;
	}

	@Override
	public JPAContainer<Account> getObject() throws Exception
	{
		JPAContainer<Account> container = JPAContainerFactory
				.makeNonCachedReadOnly(Account.class, em);
		container.setQueryModifierDelegate(new IdCollectionQueryModifier(
				new IdsCallback()
				{
					@Override
					public Set<String> evaluate()
					{
						return new HashSet<String>(accountDAO
								.findFriendIds(getId()));
					}
				}));
		return container;
	}

	@Override
	public Class<?> getObjectType()
	{
		return JPAContainer.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	private String getId()
	{
		return id == null ? SpringApplicationContext.getUser().getId() : id;
	}
}