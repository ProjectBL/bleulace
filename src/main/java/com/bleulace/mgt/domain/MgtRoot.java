package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.ProjectCreatedEvent;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@RooJavaBean
public abstract class MgtRoot implements Serializable
{
	private static final long serialVersionUID = -1911715243742088159L;

	@Id
	private String id;

	@Column(nullable = false)
	private String title;

	MgtRoot()
	{
	}

	MgtRoot(String id)
	{
		this(id, null);
	}

	MgtRoot(String id, String title)
	{
		this.id = id;
		this.title = title;
	}

	public void on(ProjectCreatedEvent event)
	{
		title = event.getTitle();
	}
}