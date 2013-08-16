package com.bleulace.domain.crm.presentation;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;

@Component
class UserDTOPropertyMap extends PropertyMap<Account, UserDTO>
{
	public UserDTOPropertyMap()
	{
		super(Account.class, UserDTO.class);
	}

	@Override
	protected void configure()
	{
		map().setFirstName(source.getContactInformation().getFirstName());
		map().setLastName(source.getContactInformation().getLastName());
	}
}