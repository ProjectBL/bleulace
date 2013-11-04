package com.bleulace.web.demo.profile;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.IdCallback;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.Presenter;
import com.google.common.collect.ForwardingMap;
import com.vaadin.addon.jpacontainer.EntityItem;

@Presenter
public class ProfilePresenter extends ForwardingMap<String, EntityItem<?>>
		implements IdCallback
{
	private final Map<String, EntityItem<?>> delegate = new HashMap<String, EntityItem<?>>();

	private Account account;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ProfileView view;

	@Autowired
	private SystemUser user;

	@Autowired
	private ApplicationContext ctx;

	@Override
	public EntityItem<?> put(String key, EntityItem<?> value)
	{
		EntityItem<?> item = super.put(key, value);
		if (item == null)
		{
			view.openTab(value);
		}
		return item;
	}

	@Override
	public EntityItem<?> remove(Object object)
	{
		EntityItem<?> item = super.remove(object);
		// clean up
		return item;
	}

	void init(String accountId)
	{
		clear();
		user.setTarget(accountId);
		account = accountDAO.findOne(accountId);
		if (account == null)
		{
			throw new IllegalArgumentException();
		}
	}

	void resourceSelected(EntityItem<?> item)
	{
		put((String) item.getItemId(), item);
		view.selectTab(item);
	}

	void tabClosing(EntityItem<?> item, Runnable callback)
	{
		callback.run();
		remove(item.getItemId());
	}

	Account getAccount()
	{
		return account;
	}

	@Override
	protected Map<String, EntityItem<?>> delegate()
	{
		return delegate;
	}

	@Override
	public String evaluate()
	{
		return account.getId();
	}
}