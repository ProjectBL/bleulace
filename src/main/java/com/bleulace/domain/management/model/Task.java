package com.bleulace.domain.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.domain.management.event.TaskMarkedEvent;
import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
@RooToString
@RooJavaBean
public class Task extends AbstractChildResource implements ManageableResource
{
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dateCompleted;

	public void on(TaskMarkedEvent event, MetaData metaData)
	{
		this.dateCompleted = new Date();
	}
}