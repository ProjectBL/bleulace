package com.bleulace.domain.management.command;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.model.ManagementLevel;

@RooJavaBean
public class EditEventCommand extends CreateEventCommand
{
	@TargetAggregateIdentifier
	private final String id;

	/**
	 * null value = regular participant. else management assignment
	 */
	private Map<String, ManagementLevel> participants = new HashMap<String, ManagementLevel>();

	public EditEventCommand(String id)
	{
		this.id = id;
	}

	public EditEventCommand(String id, String title, String location,
			Date start, Date end)
	{
		super(title, location, start, end);
		this.id = id;
	}

	@Override
	public String getId()
	{
		return id;
	}
}