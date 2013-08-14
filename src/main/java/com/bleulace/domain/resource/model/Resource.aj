package com.bleulace.domain.resource.model;

import org.springframework.data.domain.Persistable;

import com.bleulace.jpa.EntityManagerReference;

public interface Resource extends Persistable<String>
{
	public boolean isLeaf();
	
	static aspect Impl
	{
		public boolean Resource.isNew()
		{
			return EntityManagerReference.get().find(this.getClass(),
					this.getId()) == null;
		}
	}
}