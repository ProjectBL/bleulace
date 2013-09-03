package com.bleulace.domain.crm.ui.profile.field;

import com.bleulace.domain.management.presentation.EventDTO;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

public interface ParticipantFieldFactory
{
	public void setEvent(EventDTO dto);

	public Component getContent();

	public Field<?> getInviteeField();

	public Field<?> getManagerField();
}
