package com.bleulace.mgt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Task extends AbstractAnnotatedEntity implements CommentableMixin,
		AssignableMixin
{
	@Id
	@Column(nullable = false, updatable = false)
	private String id;

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