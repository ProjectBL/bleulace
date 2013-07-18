package com.bleulace.mgt.domain;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean(settersByDefault = false)
public class Task extends MgtResource implements EventSourcedEntityMixin
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
}