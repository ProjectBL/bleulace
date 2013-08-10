package com.bleulace.domain.management.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.infrastructure.QueryFactory;
import com.bleulace.utils.jpa.EntityManagerReference;

@Entity
@RooJavaBean(settersByDefault = false)
public class ManagementRoleAssignment implements Serializable
{
	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private AbstractResource resource;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account account;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private ManagementRole role;

	private ManagementRoleAssignment(AbstractResource resource,
			Account account, ManagementRole role)
	{
		this.resource = resource;
		this.account = account;
		this.role = role;
	}

	private ManagementRoleAssignment()
	{
	}

	static void revoke(String resourceId, String accountId)
	{
		QManagementRoleAssignment R = QManagementRoleAssignment.managementRoleAssignment;
		QueryFactory.delete(R).where(
				R.account.id.eq(accountId).and(R.resource.id.eq(resourceId)));
	}

	static void assign(String resourceId, String accountId, ManagementRole role)
	{
		Account account = EntityManagerReference.load(Account.class, accountId);
		QManagementRoleAssignment R = QManagementRoleAssignment.managementRoleAssignment;
		ManagementRoleAssignment assignment = QueryFactory.from(R)
				.where(R.account.eq(account).and(R.resource.id.eq(resourceId)))
				.singleResult(R);

		boolean persistent = true;
		if (assignment == null)
		{
			persistent = false;
			assignment = new ManagementRoleAssignment(
					EntityManagerReference.load(AbstractResource.class,
							resourceId), account, role);
		}
		assignment.role = role;

		if (persistent)
		{
			EntityManagerReference.get().merge(assignment);
		}
		else
		{
			EntityManagerReference.get().persist(assignment);
		}
	}

	static List<Account> findManagers(String resourceId, ManagementRole role)
	{
		QManagementRoleAssignment R = QManagementRoleAssignment.managementRoleAssignment;
		return QueryFactory.from(R)
				.where(R.resource.id.eq(resourceId).and(R.role.eq(role)))
				.list(R.account);
	}
}
