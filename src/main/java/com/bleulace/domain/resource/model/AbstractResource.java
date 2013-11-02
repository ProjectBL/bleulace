package com.bleulace.domain.resource.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.jpa.config.QueryFactory;
import com.bleulace.utils.ctx.SpringApplicationContext;

@RooJavaBean
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Configurable(preConstruction = true)
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

	@CascadeOnDelete
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
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

	public List<ManagementAssignment> getAssignments()
	{
		return assignments;
	}

	public List<String> getManagerIds()
	{
		return getManagerIds(ManagementLevel.values());
	}

	public List<String> getManagerIds(ManagementLevel... levels)
	{
		return SpringApplicationContext.getBean(ResourceDAO.class)
				.findManagerIds(getId(), levels);
	}

	public ManagementLevel getManagementLevel(String accountId)
	{
		return SpringApplicationContext.getBean(ResourceDAO.class)
				.findManagementLevel(getId(), accountId);
	}

	public void setManagementLevel(String accountId, ManagementLevel level)
	{
		QManagementAssignment a = new QManagementAssignment("a");
		ManagementAssignment assignment = QueryFactory.from(a)
				.where(a.account.id.eq(accountId).and(a.resource.id.eq(id)))
				.singleResult(a);
		if (assignment == null)
		{
			assignment = new ManagementAssignment(EntityManagerReference.load(
					Account.class, accountId), this, level);
			assignments.add(assignment);
		}
		else
		{
			assignment.setRole(level);
		}
		if (level == null)
		{
			assignments.remove(assignment);
		}
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