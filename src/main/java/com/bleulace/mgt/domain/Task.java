package com.bleulace.mgt.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.ResourceCompletedEvent;
import com.bleulace.mgt.domain.event.TaskAddedEvent;
import com.bleulace.mgt.domain.event.TaskAssignedEvent;
import com.bleulace.persistence.EventSourcedEntityMixin;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class Task extends Resource implements EventSourcedEntityMixin
{
	private static final long serialVersionUID = 6010485686197407357L;

	@Column(nullable = false)
	private boolean complete = false;

	@ManyToMany
	private Set<Account> assignees = new HashSet<Account>();

	Task(TaskAddedEvent event)
	{
		map(event);
	}

	@SuppressWarnings("unused")
	private Task()
	{
	}

	public void on(TaskAssignedEvent event)
	{
		assignees.add(EntityManagerReference.get().getReference(Account.class,
				event.getAccountId()));
	}

	@Override
	protected Set<String> getParentIds()
	{
		Set<String> ids = new HashSet<String>(SpringApplicationContext
				.getBean(BundleDAO.class).findByTaskId(getId()).getParentIds());
		return ids;
	}

	@Override
	protected Set<String> getChildIds()
	{
		return new HashSet<String>();
	}

	public void on(ResourceCompletedEvent event)
	{
		if (getId().equals(event.getId()))
		{
			complete = true;
		}
	}
}