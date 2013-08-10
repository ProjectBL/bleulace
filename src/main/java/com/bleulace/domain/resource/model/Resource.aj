package com.bleulace.domain.resource.model;

import org.springframework.data.domain.Persistable;

import com.bleulace.utils.jpa.EntityManagerReference;

public interface Resource extends Persistable<String>
{
	static aspect Impl
	{
		public boolean Resource.isNew()
		{
			return EntityManagerReference.get().find(this.getClass(),
					this.getId()) == null;
		}
	}
}