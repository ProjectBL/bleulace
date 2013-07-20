package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Task;
import com.bleulace.mgt.domain.TaskDAO;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class TaskFinderImpl extends BasicFinder<Task, TaskDTO> implements
		TaskFinder
{
	public TaskFinderImpl()
	{
		super(Task.class, TaskDTO.class);
	}

	@Autowired
	private TaskDAO taskDAO;

	@Override
	public List<TaskDTO> findByAccountId(String accountId)
	{
		return getConverter().convert(taskDAO.findByAccountId(accountId));
	}
}
