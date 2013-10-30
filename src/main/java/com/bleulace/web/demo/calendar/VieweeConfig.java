package com.bleulace.web.demo.calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.TransactionalEntityProvider;
import com.bleulace.web.SystemUser;
import com.bleulace.web.demo.timebox.ParticipantBean;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@Configuration
class VieweeConfig
{
	@Autowired
	private SystemUser user;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountDAO accountDAO;

	@Bean
	@Scope("ui")
	public JPAContainer<Account> friendsContainer(final AccountDAO dao)
	{
		JPAContainer<Account> container = new JPAContainer<Account>(
				Account.class);
		container.setEntityProvider(new TransactionalEntityProvider<Account>(
				Account.class));
		return container;
	}

	@Bean
	@Scope("ui")
	public ComboBox vieweeComboBox(
			@Qualifier("friendsContainer") final JPAContainer<Account> container,
			final VieweePresenter presenter)
	{
		final ComboBox bean = new ComboBox("Friends", container);
		bean.setItemCaptionMode(ItemCaptionMode.ITEM);
		bean.setBuffered(false);
		bean.setImmediate(true);
		bean.addValueChangeListener(new ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				String id = (String) event.getProperty().getValue();
				EntityItem<Account> item = container.getItem(id);
				if (item != null)
				{
					presenter.accountFocus(item.getEntity());
				}
			}
		});
		return bean;
	}

	@Bean
	@Scope("ui")
	public BeanContainer<String, ParticipantBean> vieweeContainer(
			@Qualifier("participantBeanContainer") BeanContainer<String, ParticipantBean> container)
	{
		return container;
	}

	@Bean
	@Scope("ui")
	public Table vieweeTable(
			final VieweePresenter presenter,
			@Qualifier("vieweeContainer") BeanContainer<String, ParticipantBean> container)
	{
		Table bean = new Table("Viewing", container);
		bean.setVisibleColumns(new Object[] { "name" });
		bean.addActionHandler(new Handler()
		{
			private final Action[] actions = new Action[] { new Action("REMOVE") };

			@Override
			public void handleAction(Action action, Object sender, Object target)
			{
				String id = (String) target;
				if (id != null)
				{
					presenter.accountBlur(id);
				}
			}

			@Override
			public Action[] getActions(Object target, Object sender)
			{
				return actions;
			}
		});
		return bean;
	}
}
