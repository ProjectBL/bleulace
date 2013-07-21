package com.bleulace.mgt.presentation;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.mgt.domain.JPAManagementPermission;
import com.bleulace.mgt.domain.Project;

@Component
public class ProjectPropertyMapping extends PropertyMap<Project, ProjectDTO>
{
	@Autowired
	private ModelMapper mapper;

	@Override
	protected void configure()
	{
		for (JPAManagementPermission assignee : source.getAssignees())
		{
			map().getAssignments().put(
					mapper.map(assignee.getAccount(), AccountDTO.class),
					assignee.getAssignment());
		}
	}
}