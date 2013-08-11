package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
@RooJavaBean
public class Task extends AbstractChildResource implements ManageableResource
{
	private boolean complete = false;

	@Override
	public boolean isLeaf()
	{
		return true;
	}
}