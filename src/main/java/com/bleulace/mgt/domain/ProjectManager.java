package com.bleulace.mgt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;

@Entity
@RooJavaBean
public class ProjectManager extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = 3699213628641518191L;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Project project;

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
		this.project = project;
		this.account = account;
		this.level = level;
	}
}