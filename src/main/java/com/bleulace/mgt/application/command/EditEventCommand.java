package com.bleulace.mgt.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import com.bleulace.mgt.presentation.EventDTO;
import com.bleulace.utils.ctx.SpringApplicationContext;

public class EditEventCommand extends CreateEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	public EditEventCommand(EventDTO dto)
	{
		Assert.notNull(dto);
		this.id = dto.getId();
		SpringApplicationContext.getBean(ModelMapper.class).map(dto, this);
	}
}