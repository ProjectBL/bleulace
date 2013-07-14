package com.bleulace.mgt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Task extends AbstractAnnotatedEntity implements CommentableMixin,
		AssignableMixin
{
	@Column(nullable = false)
	private String title;

	Task()
	{
	}
}