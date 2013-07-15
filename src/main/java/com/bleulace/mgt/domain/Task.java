package com.bleulace.mgt.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.persistence.EventSourcedEntityMixin;

@RooJavaBean(settersByDefault = false)
public class Task implements EventSourcedEntityMixin, CommentableMixin,
		AssignableMixin
{
	private static final long serialVersionUID = 6010485686197407357L;

	@Column(nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Project project;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Bundle bundle;

	@SuppressWarnings("unused")
	private Task()
	{
	}

	Task(Project project, Bundle bundle, String title)
	{
		this.title = title;
		this.project = project;
		this.bundle = bundle;
		registerAggregateRoot(project);
	}
}