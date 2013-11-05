package com.bleulace.domain.resource.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
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
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.Manager;

@Entity
@RooJavaBean
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners({ AuditingEntityListener.class })
public abstract class AbstractResource implements CompositeResource,
		Serializable
{
	@Id
	@Column(nullable = false, updatable = false)
	private String id = UUID.randomUUID().toString();

	@Column(nullable = false)
	private String title = "";

	@ManyToOne
	@JoinColumn
	private AbstractResource parent;

	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private List<AbstractResource> children = new ArrayList<AbstractResource>();

	// TODO
	@ElementCollection
	@CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = {
			"AbstractResource_ID", "ACCOUNT_ID" }))
	private List<Manager> managers = new ArrayList<Manager>();

	@CreatedDate
	private DateTime createdDate;

	@LastModifiedDate
	private DateTime lastModifiedDate;

	@CreatedBy
	private Account createdBy;

	@LastModifiedBy
	private Account lastModifiedBy;

	protected AbstractResource()
	{
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
	public List<AbstractResource> getChildren()
	{
		return children;
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