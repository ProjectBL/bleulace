package com.bleulace.crm.presentation;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.utils.ctx.SpringApplicationContext;

@RooToString
@RooEquals
@RooJavaBean
public class AccountDTO
{
	public static final AccountFinder FINDER = SpringApplicationContext
			.getBean(AccountFinder.class);

	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private byte[] image;
	private String school;
	private String work;
}