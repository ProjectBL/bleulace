package com.bleulace.ui.web.calendar;

import java.util.Date;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.EditEventCommand;
import com.bleulace.mgt.presentation.EventDTO;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
class EventEditorForm extends CustomComponent implements ClickListener,
		CommandGatewayAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -867110708166335914L;

	private BeanItem<CreateEventCommand> item;
	private FieldGroup fieldGroup;

	/**
	 * for event creation
	 */
	public EventEditorForm(Date start, Date end)
	{
		CreateEventCommand command = new CreateEventCommand();
		command.setStart(start);
		command.setEnd(end);
		item = new BeanItem<CreateEventCommand>(command);
		initFieldGroup();
		initLayout();
	}

	/**
	 * for event editing
	 */
	public EventEditorForm(EventDTO dto)
	{
		item = new BeanItem<CreateEventCommand>(new EditEventCommand(dto));
		initFieldGroup();
		initLayout();
	}

	private void initFieldGroup()
	{
		fieldGroup = new FieldGroup(item);
		fieldGroup.setBuffered(false);
	}

	private void initLayout()
	{
		FormLayout layout = new FormLayout();
		layout.addComponent(fieldGroup.buildAndBind("title"));
		layout.addComponent(fieldGroup.buildAndBind("location"));
		layout.addComponent(fieldGroup.buildAndBind("start"));
		layout.addComponent(fieldGroup.buildAndBind("end"));
		layout.addComponent(new Button("submit", this));
		setCompositionRoot(layout);
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		if (fieldGroup.isValid())
		{
			gateway().sendAndWait(item.getBean());
		}
		else
		{
			Notification.show("NOT VALID");
		}
	}
}