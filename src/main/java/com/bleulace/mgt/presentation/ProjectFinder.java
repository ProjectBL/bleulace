package com.bleulace.mgt.presentation;

import java.util.List;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.Finder;

public interface ProjectFinder extends Finder<ProjectDTO>
{
	public List<ProjectDTO> findByAssignment(String accountId,
			ManagementAssignment assignment);
}