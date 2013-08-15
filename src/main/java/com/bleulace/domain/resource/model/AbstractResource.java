package com.bleulace.domain.resource.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.equals.RooEquals;

@RooEquals
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractResource implements CompositeResource,
		Serializable
{
	@Id
	@Column(nullable = false, updatable = false)
	private String id = IdentifierFactory.getInstance().generateIdentifier();

	@EventSourcedMember
	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractChildResource> children = new ArrayList<AbstractChildResource>();

	AbstractResource()
	{
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void addChild(Resource child)
	{
		AbstractChildResource r = (AbstractChildResource) child;
		r.setRoot(getRoot());
		children.add(r);
	}

	@Override
	public void removeChild(Resource child)
	{
		children.remove(child);
	}

	@Override
	public boolean isCompatible(Resource child)
	{
		return child != null;
	}

	@Override
	public List<? extends Resource> getChildren()
	{
		return children;
	}

	public abstract AbstractRootResource getRoot();
}