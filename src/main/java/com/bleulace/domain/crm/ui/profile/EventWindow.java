package com.bleulace.domain.crm.ui.profile;

import org.apache.shiro.SecurityUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.command.EditEventCommand;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.utils.dto.Mapper;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Configurable
class EventWindow extends Window
{
	@Autowired
	private CommandGateway gateway;

	@Autowired
	@Qualifier("profileCalendar")
	private Calendar calendar;

	private final BeanFieldGroup<CreateEventCommand> group = new BeanFieldGroup<CreateEventCommand>(
			CreateEventCommand.class);

	private static final String CREATE_CAPTION = "Create Event";
	private static final String EDIT_CAPTION = "Edit Event";

	EventWindow(EventDTO dto)
	{
		System.out.println(dto.getInviteeIds().size());
		setCaption(dto.getId() == null ? CREATE_CAPTION : EDIT_CAPTION);
		group.setItemDataSource(Mapper.map(dto,
				dto.getId() == null ? new CreateEventCommand()
						: new EditEventCommand(dto.getId())));
		group.setBuffered(false);

		FormLayout form = new FormLayout();

		form.addComponent(group.buildAndBind("title"));
		form.addComponent(group.buildAndBind("location"));

		DateField startField = makeDateField();
		DateField endField = makeDateField();
		group.bind(startField, "start");
		group.bind(endField, "end");
		form.addComponent(startField);
		form.addComponent(endField);

		GuestListDisplay inviteeField = new GuestListDisplay(SecurityUtils
				.getSubject().getId(), dto);
		group.bind(inviteeField, "inviteeIds");
		form.addComponent(inviteeField);
		addActionHandler(inviteeField);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(new Button("Apply", new ApplyListener()));

		VerticalLayout content = new VerticalLayout(form, buttons);
		setContent(content);
	}

	private DateField makeDateField()
	{
		DateField field = new DateField();
		field.setResolution(Resolution.MINUTE);
		return field;
	}

	private class ApplyListener implements ClickListener
	{
		@Override
		public void buttonClick(ClickEvent event)
		{
			if (group.isValid())
			{
				try
				{
					gateway.sendAndWait(group.getItemDataSource().getBean());
					Notification.show("Changes saved.");
					calendar.markAsDirty();
					close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}