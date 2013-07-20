package com.bleulace.mgt.presentation;

import java.util.List;

import com.bleulace.utils.dto.Finder;

public interface TaskFinder extends Finder<TaskDTO>
{
	public List<TaskDTO> findByAccountId(String accountId);
}
