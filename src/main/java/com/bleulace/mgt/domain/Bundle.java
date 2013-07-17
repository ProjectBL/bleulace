package com.bleulace.mgt.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.domain.event.BundleAddedEvent;
import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean
public class Bundle extends Project implements EventSourcedEntityMixin,
		Taskable.Mixin
{
	private static final long serialVersionUID = 2492702193852559193L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Project parent;

	private boolean active = true;

	Bundle()
	{
	}

	Bundle(Project parent, BundleAddedEvent event)
	{
		super(event.getId());
		this.parent = parent;
		registerAggregateRoot(getProject());
	}

	@Override
	public void on(BundleAddedEvent event)
	{
		map(event);
	}

	@Override
	public Project getProject()
	{
		Project cursor = this;
		while (!cursor.getClass().equals(Project.class))
		{
			cursor = ((Bundle) cursor).getParent();
		}
		return cursor;
	}

	protected void markAsCompleted()
	{
		active = false;
	}
}