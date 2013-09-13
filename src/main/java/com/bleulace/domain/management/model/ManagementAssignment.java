package com.bleulace.domain.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.resource.model.AbstractChildResource;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.EntityManagerReference;

@Entity
@RooJavaBean(settersByDefault = false)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "MANAGER_ID",
		"RESOURCE_ID" }) })
public class ManagementAssignment extends AbstractChildResource
{
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "MANAGER_ID")
	private Account account;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private AbstractResource resource;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementLevel role;

	ManagementAssignment(AbstractResource resource, Account account,
			ManagementLevel role)
	{
		this.resource = resource;
		this.account = account;
		this.role = role;
	}

	private ManagementAssignment()
	{
	}

	public void on(ManagerAssignedEvent event)
	{
		if (event.getAssigneeId().equals(account.getId()))
		{
			if (event.getRole() == null)
			{
				resource.removeChild(this);
				EntityManagerReference.get().remove(this);
				return;
			}
		}
		this.role = event.getRole();
	}
}