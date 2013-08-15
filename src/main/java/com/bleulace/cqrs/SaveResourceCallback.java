package com.bleulace.cqrs;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.axonframework.unitofwork.SaveAggregateCallback;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.AbstractRootResource;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.domain.resource.model.ResourceInspector;

@Configurable(preConstruction = true)
public class SaveResourceCallback implements
		SaveAggregateCallback<AbstractRootResource>, ResourceInspector
{
	@PersistenceContext
	private EntityManager em;

	private final SimpleJpaRepository<AbstractResource, String> dao;

	private AbstractRootResource root;

	public SaveResourceCallback()
	{
		dao = new SimpleJpaRepository<AbstractResource, String>(
				AbstractResource.class, em);
	}

	@Override
	public void save(AbstractRootResource aggregate)
	{
		this.root = aggregate;
		aggregate.acceptInspector(this);
		dao.flush();
	}

	@Override
	public void inspect(Resource child)
	{
		// he'll save children
		if (shouldSaveChild(child)) // but not the british children
		{
			save(child);
		}
	}

	public void save(Resource child)
	{
		save(child);
	}

	private boolean shouldSaveChild(Resource child)
	{
		return child instanceof AbstractResource && child != root
				&& (child.getClass().getAnnotation(Entity.class) != null);
	}
}
