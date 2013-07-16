package com.bleulace.mgt.domain;

import javax.persistence.Entity;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooEquals(excludeFields = {})
@RooJavaBean(settersByDefault = false)
public class Task extends MgtRoot implements EventSourcedEntityMixin
{
	private static final long serialVersionUID = 6010485686197407357L;

	Task(Project root, TaskAddedEvent event)
	{
		this(event.getId(), event.getTitle());
		registerAggregateRoot(root);
	}

	private Task(String id, String title)
	{
		setId(id);
		setTitle(title);
	}

	@SuppressWarnings("unused")
	private Task()
	{
	}
}