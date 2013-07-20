package com.bleulace.mgt.presentation;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooToString
@RooEquals
@RooJavaBean
public class TaskDTO extends ResourceDTO
{
	boolean complete;

	private AccountDTO assignee;

	public static final TaskFinder FINDER = SpringApplicationContext
			.getBean(TaskFinder.class);
}