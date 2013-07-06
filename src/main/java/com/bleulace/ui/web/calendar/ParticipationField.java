package com.bleulace.ui.web.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
import com.bleulace.domain.calendar.ParticipationStatus;
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
class ParticipationField extends CustomField<Map>
{
	private static final long serialVersionUID = -945633119002683200L;

	private final Table table = new Table();

	private final ComboBox comboBox = new ComboBox();

	private final BeanContainer<Long, Entry<Account, ParticipationStatus>> tableContainer;

	public ParticipationField(JPACalendarEvent event)
	{
		tableContainer = new BeanContainer<Long, Entry<Account, ParticipationStatus>>(
				Entry.class);
		tableContainer
				.setBeanIdResolver(new BeanIdResolver<Long, Map.Entry<Account, ParticipationStatus>>()
				{
					private static final long serialVersionUID = 1L;

					@Override
					public Long getIdForBean(
							Entry<Account, ParticipationStatus> bean)
					{
						return bean.getKey().getId();
					}
				});

		setCaption("Participants");
		table.setContainerDataSource(tableContainer);
		table.setSelectable(true);
		table.setImmediate(true);
		table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		table.addItemClickListener(new ItemClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.isDoubleClick())
				{
					table.removeItem(event.getItemId());
				}
			}
		});

		BeanContainer<Long, Account> comboBoxContainer = new BeanContainer<Long, Account>(
				Account.class);
		comboBoxContainer.setBeanIdProperty("id");
		comboBoxContainer.addAll(Account.findAll());

		comboBox.setContainerDataSource(comboBoxContainer);
		comboBox.setImmediate(true);
		comboBox.addValueChangeListener(new ComboBoxValueChangeListener());
		comboBox.setWidth("200px");
		comboBox.setItemCaptionPropertyId("name");
		comboBox.addValueChangeListener(this);
	}

	@Override
	public void setWidth(String width)
	{
		super.setWidth(width);
		comboBox.setWidth(width);
		table.setWidth(width);
	}

	@Override
	public Class<Map> getType()
	{
		return Map.class;
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
	protected Map getInternalValue()
	{
		List<Long> accountIds = tableContainer.getItemIds();
		Map<Account, ParticipationStatus> map = new HashMap<Account, ParticipationStatus>();
		for (Long id : accountIds)
		{
			Entry<Account, ParticipationStatus> entry = tableContainer.getItem(
					id).getBean();
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setInternalValue(Map newValue)
	{
		tableContainer.removeAllItems();
		tableContainer.addAll(newValue.entrySet());
	}

	class ComboBoxValueChangeListener implements ValueChangeListener
	{
		private static final long serialVersionUID = 3827990393039907281L;

		@Override
		public void valueChange(Property.ValueChangeEvent valueChangeEvent)
		{
			@SuppressWarnings("unchecked")
			final BeanItem<Account> item = (BeanItem<Account>) comboBox
					.getItem(comboBox.getValue());
			tableContainer.addBean(new Entry<Account, ParticipationStatus>()
			{
				private ParticipationStatus value = ParticipationStatus.PENDING;
				private Account account = item.getBean();

				@Override
				public ParticipationStatus setValue(ParticipationStatus value)
				{
					this.value = value;
					return value;
				}

				@Override
				public ParticipationStatus getValue()
				{
					return value;
				}

				@Override
				public Account getKey()
				{
					return account;
				}
			});
			table.markAsDirty();
			// event.getParticipants().put(item.getBean(),
			// ParticipationStatus.PENDING);
		}
	}

	class TableItemClickListener implements ItemClickListener
	{
		private static final long serialVersionUID = -3756500964111913512L;

		@Override
		public void itemClick(ItemClickEvent event)
		{
			Account current = Account.current();
			if (current != null)
			{
				Long userId = (Long) event.getItem().getItemProperty("key.id")
						.getValue();
				if (current.getId().equals(userId))
				{
					return;
				}
			}
			if (event.isDoubleClick())
			{
				@SuppressWarnings("unchecked")
				BeanItem<Entry<Account, ParticipationStatus>> item = (BeanItem<Entry<Account, ParticipationStatus>>) event
						.getItem();
				tableContainer.removeItem(item.getBean().getKey().getId());
			}
		}
	}
}