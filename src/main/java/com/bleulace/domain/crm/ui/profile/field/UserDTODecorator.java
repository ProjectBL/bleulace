package com.bleulace.domain.crm.ui.profile.field;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.dto.Mapper;

@RooJavaBean
class UserDTODecorator extends UserDTO
{
	private RsvpStatus status;

	private ManagementLevel level;

	public UserDTODecorator(UserDTO dto, RsvpStatus status,
			ManagementLevel level)
	{
		Mapper.map(dto, this);
		this.status = status;
		this.level = level;
	}
}