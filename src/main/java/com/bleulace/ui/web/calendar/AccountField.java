package com.bleulace.ui.web.calendar;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.bleulace.domain.account.QAccount;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("rawtypes")
@Configurable(preConstruction = true)
class AccountField extends CustomField<Collection>
{
	private static final long serialVersionUID = -945633119002683200L;

	@PersistenceContext
	private EntityManager entityManager;

	private final Table table = new Table();
	private final ComboBox comboBox = new ComboBox();

	private final BeanContainer<String, Account> tableContainer = makeContainer();

	public AccountField(String caption)
	{
		setCaption(caption);
		table.setContainerDataSource(tableContainer);
		table.setSelectable(true);
		table.setImmediate(true);
		table.setVisibleColumns(new Object[] { "name" });
		// table.setColumnHeaders(new String[] { "active" });
		table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		table.addItemClickListener(new TableItemClickListener());
		// table.setWidth("160px");

		comboBox.setContainerDataSource(JPAContainerFactory.makeReadOnly(
				Account.class, entityManager));
		comboBox.setImmediate(true);
		comboBox.addValueChangeListener(new ComboBoxValueChangeListener());
		comboBox.setWidth("160px");
		comboBox.setItemCaptionPropertyId("name");

	}

	@Override
	public void setWidth(String width)
	{
		super.setWidth(width);
		comboBox.setWidth(width);
		table.setWidth(width);
	}

	@Override
	public Class<Set> getType()
	{
		return Set.class;
	}

	@Override
	protected Component initContent()
	{
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponents(table, comboBox);
		return layout;
	}

	@Override
	protected Collection getInternalValue()
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).where(a.id.in(tableContainer.getItemIds()))
				.list(a);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setInternalValue(Collection newValue)
	{
		tableContainer.removeAllItems();
		tableContainer.addAll(newValue);
	}

	class ComboBoxValueChangeListener implements ValueChangeListener
	{
		private static final long serialVersionUID = 3827990393039907281L;

		@Override
		public void valueChange(Property.ValueChangeEvent event)
		{
			JPAContainerItem<Account> item = (JPAContainerItem<Account>) comboBox
					.getItem(comboBox.getValue());
			tableContainer.addBean(item.getEntity());
		}
	}

	class TableItemClickListener implements ItemClickListener
	{
		private static final long serialVersionUID = -3756500964111913512L;

		@Override
		public void itemClick(ItemClickEvent event)
		{
			if (event.isDoubleClick())
			{
				BeanItem<Account> item = (BeanItem<Account>) event.getItem();
				tableContainer.removeItem(item.getBean().getId());
			}
		}

	}

	private BeanContainer<String, Account> makeContainer()
	{
		BeanContainer<String, Account> container = new BeanContainer<String, Account>(
				Account.class);
		container.setBeanIdProperty("id");
		container.setBeanIdResolver(new BeanIdResolver<String, Account>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdForBean(Account bean)
			{
				return bean.getId();
			}
		});
		return container;
	}
}