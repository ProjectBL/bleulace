package com.bleulace.domain.crm.ui.profile.field;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@UIComponent
class ParticipantFieldFactoryImpl implements ParticipantFieldFactory
{
	@Autowired
	private ParticipantFieldMediator mediator;

	@Autowired
	private InviteeField inviteeField;

	@Autowired
	private ManagerField managerField;

	@Autowired
	@Qualifier("participantTable")
	private Table participantTable;

	@Autowired
	@Qualifier("candidateBox")
	private ComboBox candidateBox;

	private Component content;

	@PostConstruct
	protected void init()
	{
		VerticalLayout layout = new VerticalLayout(candidateBox,
				participantTable);
		content = layout;
	}

	@Override
	public void setEvent(EventDTO dto)
	{
		mediator.setEvent(dto);
	}

	@Override
	public com.vaadin.ui.Component getContent()
	{
		return content;
	}

	@Override
	public Field<?> getInviteeField()
	{
		return inviteeField;
	}

	@Override
	public Field<?> getManagerField()
	{
		return managerField;
	}
}