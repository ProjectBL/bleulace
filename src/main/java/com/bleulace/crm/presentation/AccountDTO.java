package com.bleulace.crm.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.utils.ctx.SpringApplicationContext;

@RooToString
@RooJavaBean
public class AccountDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2186190259927138155L;

	public static final AccountFinder FINDER = SpringApplicationContext
			.getBean(AccountFinder.class);

	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private byte[] image;
	private String school;
	private String work;
	private List<StatusUpdateDTO> statusUpdates = new ArrayList<StatusUpdateDTO>();

	public String getCaption()
	{
		return firstName + " " + lastName;
	}
}