package com.bleulace.domain.crm.ui.profile;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.util.Assert;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.EnablePush;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ListSelect;

class ResourceListComponent extends CustomComponent implements
		ValueChangeListener
{
	private transient final EventBus eventBus;

	private final String resourceType;

	private final BeanContainer<String, ResourceTableEntry> container = makeContainer();

	private final ListSelect listSelect = makeListSelect();

	public ResourceListComponent(String resourceType, EventBus eventBus)
	{
		Assert.notNull(resourceType);
		Assert.notNull(eventBus);

		this.resourceType = resourceType;
		this.eventBus = eventBus;

		setCompositionRoot(listSelect);
	}

	public ResourceListComponent(String resourceType)
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
	@EnablePush
	public void valueChange(ValueChangeEvent event)
	{
		eventBus.publish(GenericEventMessage
				.asEventMessage(new ResourceSelectedEvent((String) event
						.getProperty().getValue(), resourceType)));
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

	private ListSelect makeListSelect()
	{
		ListSelect select = new ListSelect(null, container);
		select.setImmediate(true);
		select.addValueChangeListener(this);
		select.setItemCaptionPropertyId("title");
		select.setNullSelectionAllowed(true);
		select.setSizeFull();
		select.setRows(4);
		return select;
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