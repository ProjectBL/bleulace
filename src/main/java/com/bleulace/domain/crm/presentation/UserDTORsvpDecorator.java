package com.bleulace.domain.crm.presentation;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.dto.Mapper;

@RooJavaBean
public class UserDTORsvpDecorator extends UserDTO
{
	private RsvpStatus status;

	public UserDTORsvpDecorator(UserDTO dto, RsvpStatus status)
	{
		Mapper.map(dto, this);
		this.status = status;
	}
}