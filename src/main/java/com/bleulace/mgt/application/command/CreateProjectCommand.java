package com.bleulace.mgt.application.command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CreateProjectCommand
{
	@NotEmpty
	private String name = "";
}