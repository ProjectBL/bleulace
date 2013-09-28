package com.bleulace.domain.resource.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.springframework.roo.addon.equals.RooEquals;

import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooEquals
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractResource implements CompositeResource,
		Serializable
{
	@Id
	@Column(nullable = false, updatable = false)
	private String id = UUID.randomUUID().toString();

	@ManyToOne
	@JoinColumn
	private AbstractResource parent;

	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private List<AbstractResource> children = new ArrayList<AbstractResource>();

	protected AbstractResource()
	{
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Resource getParent()
	{
		return parent;
	}

	void setParent(AbstractResource parent)
	{
		this.parent = parent;
	}

	@Override
	public void addChild(Resource child)
	{
		AbstractResource r = (AbstractResource) child;
		r.setParent(this);
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Resource> List<T> getChildren(Class<T> clazz)
	{
		if (this.isNew() || !AbstractResource.class.isAssignableFrom(clazz))
		{
			List<T> list = new ArrayList<T>();
			for (Resource c : getChildren())
			{
				if (c.getClass().isAssignableFrom(clazz))
				{
					list.add((T) c);
				}
			}
			return list;
		}
		return (List<T>) SpringApplicationContext.getBean(ResourceDAO.class)
				.findChildren(id, (Class<? extends AbstractResource>) clazz);
	}
}