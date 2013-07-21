package com.bleulace.mgt.presentation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooToString
@RooEquals
@RooJavaBean
public class ProjectDTO extends ResourceDTO
{
	private Map<AccountDTO, ManagementAssignment> assignments = new HashMap<AccountDTO, ManagementAssignment>();

	public static final ProjectFinder FINDER = SpringApplicationContext
			.getBean(ProjectFinder.class);
}