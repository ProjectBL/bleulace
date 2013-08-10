package com.bleulace.domain.resource.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import com.bleulace.jpa.EventSourcedEntityMixin;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractChildResource extends AbstractResource implements
		CompositeResource, EventSourcedEntityMixin
{

	@CascadeOnDelete
	@EventSourcedMember
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractResource> children = new ArrayList<AbstractResource>();

	@ManyToOne
	private AbstractRootResource root;

	protected AbstractChildResource()
	{
	}

	protected AbstractChildResource(AbstractRootResource root)
	{
		setRoot(root);
	}

	@Override
	public AbstractRootResource getRoot()
	{
		return root;
	}

	protected void setRoot(AbstractRootResource root)
	{
		this.root = root;
	}

	@Override
	public List<? extends Resource> getChildren()
	{
		return children;
	}

	@Override
	public void addChild(Resource child)
	{
		AbstractChildResource res = (AbstractChildResource) child;
		res.setRoot(getRoot());
		children.add(res);
	}

	@Override
	public void removeChild(Resource child)
	{
		children.remove(child);
	}

	@Override
	public boolean isCompatible(Resource child)
	{
		return child instanceof AbstractChildResource;
	}
}