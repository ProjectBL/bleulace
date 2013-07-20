package com.bleulace.crm.presentation;

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.ctx.SpringApplicationContext;

@RooJavaBean
public class GroupDTO
{
	public GroupDTO()
	{
	}

	private String id;

	private String title;

	private List<AccountDTO> members;

	public static final GroupFinder FINDER = SpringApplicationContext.get()
			.getBean(GroupFinder.class);
}