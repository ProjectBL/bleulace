package com.bleulace.crm.presentation;

import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.Account;
import com.bleulace.utils.dto.DefaultDTOConverter;

@Component
class AccountDTOConverter extends DefaultDTOConverter<Account, AccountDTO>
{
	private AccountDTOConverter()
	{
		super(AccountDTO.class);
	}
}