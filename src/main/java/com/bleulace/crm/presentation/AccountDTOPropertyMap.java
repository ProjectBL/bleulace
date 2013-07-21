package com.bleulace.crm.presentation;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.bleulace.crm.domain.Account;

//@Component
public class AccountDTOPropertyMap extends PropertyMap<Account, AccountDTO>
{
	@Override
	protected void configure()
	{
		new ModelMapper().map(source, map());
	}
}
