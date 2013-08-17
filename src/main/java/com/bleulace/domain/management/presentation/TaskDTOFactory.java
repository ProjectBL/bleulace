package com.bleulace.domain.management.presentation;

import com.bleulace.utils.dto.DTOFactory;
import com.bleulace.utils.dto.Factory;

@Factory(makes = TaskDTO.class)
class TaskDTOFactory implements DTOFactory<TaskDTO>
{
	@Override
	public TaskDTO make()
	{
		return new TaskDTOImpl();
	}

}
