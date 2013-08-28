package com.bleulace.domain.crm.ui.profile;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.presentation.UserDTO;

@Component
class InfoBlockPropertyMap extends PropertyMap<UserDTO, InfoBlock>
{
	@Override
	protected void configure()
	{
		map().setName(source.getFirstName());
	}
}
