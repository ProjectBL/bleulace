package com.bleulace.domain.crm.ui.profile;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.util.Assert;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;

class ResourceTable extends CustomComponent implements ItemClickListener
{
	private transient final EventBus eventBus;

	private final String resourceType;

	private final BeanContainer<String, ResourceTableEntry> container = makeContainer();

	private final Table table = makeTable();

	public ResourceTable(String resourceType, EventBus eventBus)
	{
		Assert.notNull(resourceType);
		Assert.notNull(eventBus);

		this.resourceType = resourceType;
		this.eventBus = eventBus;

		setCompositionRoot(table);
	}

	public ResourceTable(String resourceType)
	{
		this(resourceType, SpringApplicationContext.getBean(EventBus.class,
				"uiBus"));
	}

	public void addResource(String id, String title)
	{
		container.addBean(new ResourceTableEntry(id, title));
	}

	public void clearAllResources()
	{
		container.removeAllItems();
	}

	@Override
	public void itemClick(ItemClickEvent event)
	{
		eventBus.publish(GenericEventMessage
				.asEventMessage(new ResourceSelectedEvent((String) event
						.getItemId(), resourceType)));
	}

	public static class ResourceSelectedEvent
	{
		private final String resourceType;
		private final String resourceId;

		public ResourceSelectedEvent(String resourceId, String resourceType)
		{
			this.resourceId = resourceId;
			this.resourceType = resourceType;
		}

		public String getResourceId()
		{
			return resourceId;
		}

		public String getResourceType()
		{
			return resourceType;
		}
	}

	private BeanContainer<String, ResourceTableEntry> makeContainer()
	{
		BeanContainer<String, ResourceTableEntry> container = new BeanContainer<String, ResourceTableEntry>(
				ResourceTableEntry.class);
		container.setBeanIdProperty("id");
		return container;
	}

	private Table makeTable()
	{
		Table table = new Table("", container);
		table.setSizeFull();
		table.setVisibleColumns("title");
		table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		table.addItemClickListener(this);
		table.setPageLength(5);
		return table;
	}

	private class ResourceTableEntry
	{
		private final String id;
		private final String title;

		ResourceTableEntry(String id, String title)
		{
			Assert.notNull(id);
			Assert.notNull(title);
			this.id = id;
			this.title = title;
		}

		@SuppressWarnings("unused")
		public String getId()
		{
			return id;
		}

		@SuppressWarnings("unused")
		public String getTitle()
		{
			return title;
		}
	}
}