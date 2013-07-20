package com.bleulace.crm.presentation;

import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.AccountGroup;
import com.bleulace.utils.dto.DefaultDTOConverter;

@Component
class GroupDTOConverter extends DefaultDTOConverter<AccountGroup, GroupDTO>
{
	public GroupDTOConverter()
	{
		super(GroupDTO.class);
	}
}