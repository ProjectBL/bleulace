package com.bleulace.domain.management.presentation;

import java.util.List;

import com.bleulace.utils.dto.Finder;

public interface ProjectFinder extends Finder<ProjectDTO>
{
	public List<ProjectDTO> findByManager(String accountId);
}