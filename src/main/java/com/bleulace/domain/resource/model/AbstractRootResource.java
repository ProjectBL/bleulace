package com.bleulace.domain.resource.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.bleulace.jpa.EventSourcedAggregateRootMixin;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ROOT")
public class AbstractRootResource extends AbstractChildResource implements
		CompositeResource, EventSourcedAggregateRootMixin
{
	protected AbstractRootResource()
	{
	}

	@Override
	public AbstractRootResource getRoot()
	{
		return this;
	}
}