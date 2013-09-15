package com.bleulace.domain.resource.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractChildResource extends AbstractResource implements
		CompositeResource
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