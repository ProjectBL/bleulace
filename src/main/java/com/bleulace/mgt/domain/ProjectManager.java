package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

@Entity
@RooJavaBean
public class ProjectManager implements Serializable
{
	private static final long serialVersionUID = 3699213628641518191L;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Project project;

	@Id
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Account account;

	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private ManagementLevel level;

	@SuppressWarnings("unused")
	private ProjectManager()
	{
	}

	public ProjectManager(Project project, Account account,
			ManagementLevel level)
	{
		Assert.noNullElements(new Object[] { project, account, level });
		this.project = project;
		this.account = account;
		this.level = level;
	}
}