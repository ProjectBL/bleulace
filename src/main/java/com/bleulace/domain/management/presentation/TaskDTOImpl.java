package com.bleulace.domain.management.presentation;

import javax.validation.constraints.NotNull;

class TaskDTOImpl extends ManageableResourceDTOImpl implements TaskDTO
{
	@NotNull
	private boolean complete;

	@Override
	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
}
