package com.bleulace.mgt.domain;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.persistence.EventSourcedEntityMixin;

@RooJavaBean(settersByDefault = false)
public class Task implements EventSourcedEntityMixin, CommentableMixin,
		AssignableMixin
{
	private static final long serialVersionUID = 6010485686197407357L;

	@Column(nullable = false)
	private String title;

	@SuppressWarnings("unused")
	private Task()
	{
	}

	Task(Project goal, String title)
	{
		this.title = title;
		registerAggregateRoot(goal);
	}
}