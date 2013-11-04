package com.bleulace.domain.resource.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.utils.ctx.SpringApplicationContext;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners({ AuditingEntityListener.class })
public abstract class AbstractResource implements CompositeResource,
		Serializable
{
	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private String title = "";

	@ManyToOne
	@JoinColumn
	private AbstractResource parent;

	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private List<AbstractResource> children = new ArrayList<AbstractResource>();

	@ElementCollection
	private List<ManagementAssignment> assignments = new ArrayList<ManagementAssignment>();

	@CreatedDate
	private DateTime createdDate;

	@LastModifiedDate
	private DateTime lastModifiedDate;

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

	@Override
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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

	public List<ManagementAssignment> getAssignments()
	{
		return assignments;
	}

	public void setAssignments(List<ManagementAssignment> assignments)
	{
		this.assignments = assignments;
	}

	@Override
	public String toString()
	{
		return getTitle();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (null == obj)
		{
			return false;
		}

		if (this == obj)
		{
			return true;
		}

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		AbstractResource that = (AbstractResource) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}
}