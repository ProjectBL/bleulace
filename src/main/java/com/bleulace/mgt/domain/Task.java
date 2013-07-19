package com.bleulace.mgt.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.persistence.EventSourcedEntityMixin;
import com.bleulace.utils.ctx.SpringApplicationContext;

@Entity
@RooJavaBean(settersByDefault = false)
public class Task extends MgtResource implements EventSourcedEntityMixin,
		Assignable.Mixin<TaskAssignment>
{
	private static final long serialVersionUID = 6010485686197407357L;

	Task(TaskAddedEvent event)
	{
		map(event);
	}

	@SuppressWarnings("unused")
	private Task()
	{
	}

	@Override
	protected Set<String> getAncestorIds()
	{
		Set<String> ids = new HashSet<String>(SpringApplicationContext.get()
				.getBean(BundleFinder.class).findOneByTask(this)
				.getAncestorIds());
		return ids;
	}
}