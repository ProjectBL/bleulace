package com.bleulace.domain.management.presentation;

import java.util.List;

public interface BundleDTO extends ProjectDTO
{
	public List<TaskDTO> getTasks();
}