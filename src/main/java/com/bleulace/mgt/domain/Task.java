package com.bleulace.mgt.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Task extends AbstractAnnotatedEntity implements CommentableMixin,
		AssignableMixin
{
	@Id
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private String title;

	Task()
	{
	}
}