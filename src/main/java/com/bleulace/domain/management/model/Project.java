package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooJavaBean
public class Project extends AbstractResource implements ManageableResource
{
	public Project()
	{
	}

	@Override
	public boolean isComplete()
	{
		return getProgress().isComplete();
	}

	@Override
	public void setComplete(boolean complete)
	{
		for (ManageableResource r : getChildren(ManageableResource.class))
		{
			r.setComplete(complete);
		}
	}
}