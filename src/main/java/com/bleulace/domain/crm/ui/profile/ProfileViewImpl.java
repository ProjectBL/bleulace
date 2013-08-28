package com.bleulace.domain.crm.ui.profile;

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

@VaadinView("profileView")
class ProfileViewImpl extends CustomComponent implements ProfileView, View
{
	@Autowired
	@Qualifier("uiBus")
	private transient EventBus uiBus;

	private final InfoBlock infoBlock = new InfoBlock();

	ProfileViewImpl()
	{
		setCompositionRoot(infoBlock);
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
	}
}