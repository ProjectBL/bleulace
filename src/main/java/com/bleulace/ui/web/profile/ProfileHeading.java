package com.bleulace.ui.web.profile;

import org.springframework.util.Assert;

import com.bleulace.crm.presentation.AccountDTO;
import com.vaadin.ui.CustomComponent;

class ProfileHeading extends CustomComponent
{
	private static final long serialVersionUID = -7333744848084785712L;

	private final AccountDTO dto;

	ProfileHeading(AccountDTO dto)
	{
		Assert.notNull(dto);
		this.dto = dto;
	}
}
