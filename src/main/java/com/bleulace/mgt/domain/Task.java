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

	@JoinColumn(nullable = false, updatable = false)
	@ManyToOne
	private Project project;

	@SuppressWarnings("unused")
	private Task()
	{
	}

	Task(Project project, String title)
	{
		this.project = project;
		this.title = title;
		registerAggregateRoot(project);
	}
}