package com.bleulace.mgt.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class Project extends AbstractAnnotatedAggregateRoot<String>
{
	private static final long serialVersionUID = -1998536878318608268L;

	@Id
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private String name;

	Project()
	{
	}
}