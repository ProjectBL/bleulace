package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import com.bleulace.mgt.domain.event.TaskAddedEvent;

public interface Taskable
{
	public String getId();

	public List<Task> getTasks();

	public Project getProject();

	interface Mixin extends Taskable
	{
		static aspect Impl
		{
			@EventSourcedMember
			@CascadeOnDelete
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
					tasks.add(new Task(event));
				}
			}
		}
	}
}
