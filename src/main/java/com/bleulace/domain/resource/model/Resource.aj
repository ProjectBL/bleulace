package com.bleulace.domain.resource.model;

import org.springframework.data.domain.Persistable;

import com.bleulace.jpa.EntityManagerReference;

public interface Resource extends Persistable<String>
{
	public String getTitle();

	public void acceptInspector(ResourceInspector inspector);

	static aspect Impl
	{
		public boolean Resource.isNew()
		{
			return EntityManagerReference.get().find(this.getClass(),
					this.getId()) == null;
		}

		public void Resource.acceptInspector(ResourceInspector inspector)
		{
			inspector.inspect(this);
		}
	}
}