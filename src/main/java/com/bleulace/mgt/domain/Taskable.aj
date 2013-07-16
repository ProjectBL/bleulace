package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.mgt.domain.event.TaskEvent;
import com.bleulace.mgt.domain.event.TaskEventFilter;
import com.bleulace.persistence.EventFilterSpecification;
import com.bleulace.persistence.Filter;

public interface Taskable
{
	public String getId();

	public List<Task> getTasks();

	public Project getProject();

	interface Mixin extends Taskable
	{
		static aspect Impl
		{
			@OneToMany(cascade = CascadeType.ALL)
			private List<Task> Mixin.tasks = new ArrayList<Task>();

			public List<Task> Mixin.getTasks()
			{
				return tasks;
			}

			public void Mixin.on(TaskAddedEvent event)
			{
				if (event.getBundleId().equals(this.getId()))
				{
					tasks.add(new Task(this.getProject(), event));
				}
			}
			
			@Filter
			private EventFilterSpecification<TaskEvent> Mixin.getTaskFilter()
			{
				return new TaskEventFilter(this);
			}
		}
	}
}
