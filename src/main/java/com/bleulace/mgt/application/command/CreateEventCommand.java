package com.bleulace.mgt.application.command;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.DateRange;
import com.bleulace.mgt.domain.FutureRange;

@RooJavaBean(settersByDefault = false)
public class CreateEventCommand extends CreateProjectCommand
{
	@FutureRange
	private DateRange range = new DateRange();

	public CreateEventCommand()
	{
	}
}