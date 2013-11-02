package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractResource;

@Entity
public class Project extends AbstractResource implements ManageableResource
{
	private boolean complete = false;

	public Project()
	{
	}

	@Override
	public boolean isComplete()
	{
		return complete;// getProgress().isComplete();
	}

	@Override
	public void setComplete(boolean complete)
	{
		this.complete = complete;
		// for (ManageableResource r :
		// getChildren(ManageableResource.class)){r.setComplete(complete);}
	}
}