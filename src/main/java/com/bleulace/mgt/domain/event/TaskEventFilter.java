package com.bleulace.mgt.domain.event;

import com.bleulace.mgt.domain.Taskable;
import com.bleulace.persistence.EventFilterSpecification;
import com.bleulace.utils.spec.CompositeSpecification;

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
