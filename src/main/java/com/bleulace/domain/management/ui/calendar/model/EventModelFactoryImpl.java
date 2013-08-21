package com.bleulace.domain.management.ui.calendar.model;

import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.utils.dto.Mapper;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class EventModelFactoryImpl implements EventModelFactory, CommandGatewayAware
{
	@Override
	public CreateModelAdapter make(Date start, Date end, CalendarViewContext ctx)
	{
		CreateModelAdapter model = new CreateModelAdapter(ctx);
		model.setStart(start);
		model.setEnd(end);
		return model;
	}

	@Override
	public EditModelAdapter make(EventDTO dto, CalendarViewContext ctx)
	{
		EditModelAdapter model = new EditModelAdapter(dto.getId(), ctx);
		Mapper.map(dto, model);
		return model;
	}
}
