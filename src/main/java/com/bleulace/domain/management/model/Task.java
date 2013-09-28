package com.bleulace.domain.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooToString
@RooJavaBean
public class Task extends AbstractResource implements ManageableResource
{
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dateCompleted;

	@Override
	public void setComplete(boolean complete)
	{
		this.dateCompleted = complete ? new Date() : null;
	}

	@Override
	public boolean isComplete()
	{
		return dateCompleted != null;
	}
}