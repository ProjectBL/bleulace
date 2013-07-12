package com.bleulace.accountRelations.application.command;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class LoginCommand
{
	private String username = "";
	private String password = "";
	private Boolean rememberMe = false;
}