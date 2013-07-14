package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public interface TaskableMixin 
{
	static aspect Impl
	{
		@OneToMany(cascade = CascadeType.ALL)
		private List<Task> TaskableMixin.tasks = new ArrayList<Task>();
		
		public List<Task> TaskableMixin.getTasks()
		{
			return tasks;
		}
		
		public void TaskableMixin.addTask(Task task)
		{
			tasks.add(task);
		}
	}
}
