package com.bleulace.domain.crm.ui.profile;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.axonframework.domain.GenericEventMessage;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.utils.dto.Mapper;
import com.bleulace.web.VaadinView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

@VaadinView("profileView")
class ProfileViewImpl extends CustomComponent implements ProfileView, View
{
	@Autowired
	@Qualifier("uiBus")
	private transient EventBus uiBus;

	@Autowired
	@Qualifier("actionTabSheet")
	private TabSheet tabSheet;

	private final ResourceTable projects = new ResourceTable("project");
	private final ResourceTable events = new ResourceTable("events");

	private final InfoBlock infoBlock = new InfoBlock();

	ProfileViewImpl()
	{
	}

	@PostConstruct
	protected void init()
	{
		VerticalLayout layout = new VerticalLayout(tabSheet, infoBlock,
				projects, events);
		layout.setWidth("400px");
		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		uiBus.publish(GenericEventMessage.asEventMessage(event));
	}

	@Override
	public void setInfo(UserDTO dto)
	{
		Mapper.map(dto, infoBlock);
		projects.setCaption(dto.getFirstName() + "'s projects");
		events.setCaption(dto.getFirstName() + "'s events");
	}

	@Override
	public void setProjects(Map<String, String> idTitleMap)
	{
		populateResourceTable(projects, idTitleMap);
	}

	@Override
	public void setEvents(Map<String, String> idTitleMap)
	{
		populateResourceTable(events, idTitleMap);
	}

	private void populateResourceTable(ResourceTable table,
			Map<String, String> data)
	{
		table.clearAllResources();
		for (Entry<String, String> entry : data.entrySet())
		{
			table.addResource(entry.getKey(), entry.getValue());
		}
	}
}