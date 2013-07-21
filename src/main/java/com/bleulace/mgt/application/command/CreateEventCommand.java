package com.bleulace.mgt.application.command;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateEventCommand extends CreateProjectCommand implements
		DateRangeMixin
{
	@NotNull
	private String location = "";
}