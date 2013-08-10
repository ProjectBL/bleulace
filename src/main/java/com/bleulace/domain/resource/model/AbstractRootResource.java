package com.bleulace.domain.resource.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.bleulace.jpa.EventSourcedAggregateRootMixin;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ROOT")
public class AbstractRootResource extends AbstractResource implements
		CompositeResource, EventSourcedAggregateRootMixin
{
	@Override
	public AbstractRootResource getRoot()
	{
		return this;
	}
}