package com.bleulace.domain.resource.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bleulace.jpa.EventSourcedEntityMixin;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractChildResource extends AbstractResource implements
		CompositeResource, EventSourcedEntityMixin
{
	@ManyToOne
	@JoinColumn(updatable = false)
	private AbstractRootResource root;

	@Override
	public AbstractRootResource getRoot()
	{
		return root;
	}

	void setRoot(AbstractRootResource root)
	{
		this.root = root;
	}
}