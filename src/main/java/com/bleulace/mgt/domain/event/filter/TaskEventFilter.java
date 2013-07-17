package com.bleulace.mgt.domain.event.filter;

import com.bleulace.ddd.spec.CompositeSpecification;
import com.bleulace.mgt.domain.Taskable;
import com.bleulace.persistence.EventFilterSpecification;

public class TaskEventFilter extends CompositeSpecification<TaskEvent>
		implements EventFilterSpecification<TaskEvent>
{
	private final Taskable task;

	public TaskEventFilter(Taskable task)
	{
		this.task = task;
	}

	@Override
	public boolean isSatisfiedBy(TaskEvent candidate)
	{
		return task.getId().equals(candidate.getId());
	}

	@Override
	public Class<TaskEvent> candidateClass()
	{
		return TaskEvent.class;
	}

}
