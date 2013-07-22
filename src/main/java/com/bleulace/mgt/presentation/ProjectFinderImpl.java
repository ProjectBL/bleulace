package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.mgt.domain.Project;
import com.bleulace.mgt.domain.ProjectDAO;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class ProjectFinderImpl extends BasicFinder<Project, ProjectDTO>
		implements ProjectFinder
{
	@Autowired
	private ProjectDAO projectDAO;

	public ProjectFinderImpl()
	{
		super(Project.class, ProjectDTO.class);
	}

	@Override
	public List<ProjectDTO> findByAssignment(String accountId,
			ManagementAssignment assignment)
	{
		return getConverter().convert(
				projectDAO.findByAssignment(accountId, assignment,
				// Arrays.asList(Project.class)));
						Project.class));
	}
}