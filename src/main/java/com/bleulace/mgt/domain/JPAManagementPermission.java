package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

@Entity
@RooJavaBean(settersByDefault = false)
public class JPAManagementPermission implements Permission, Serializable
{
	private static final long serialVersionUID = -251787870491515448L;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "ACCOUNT_ID")
	private Account account;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, name = "PROJECT_ID")
	private Project project;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ManagementAssignment assignment;

	JPAManagementPermission(Account account, Project project,
			ManagementAssignment assignment)
	{
		Assert.noNullElements(new Object[] { account, project, assignment });
		this.account = account;
		this.project = project;
		this.assignment = assignment;
	}

	@SuppressWarnings("unused")
	private JPAManagementPermission()
	{
	}

	@Override
	public boolean implies(Permission p)
	{
		return new ManagementPermission(project, assignment).implies(p);
	}
}