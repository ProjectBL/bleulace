package com.bleulace.mgt.domain;

import java.util.List;

public interface Taskable
{
	public List<Task> getTasks();

	public void addTask(Task task);
}