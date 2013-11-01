package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.resource.model.AbstractResource;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Window;

@Component("managerBox")
@Scope("prototype")
class ManagerBoxFactoryBean implements FactoryBean<Window>
{
	@Autowired
	@Qualifier("friendContainer")
	private JPAContainer<Account> candidates;

	private final ManagerBoxPresenter presenter;

	ManagerBoxFactoryBean(AbstractResource resource)
	{
		presenter = new ManagerBoxPresenter(resource);
	}

	@SuppressWarnings("unused")
	private ManagerBoxFactoryBean()
	{
		this(new PersistentEvent());
	}

	@Override
	public Window getObject() throws Exception
	{
		ManagerBox view = new ManagerBox(presenter);
		presenter.setView(view);
		return view;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Window.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}