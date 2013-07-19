package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(settersByDefault = false)
public class ManagementPermission implements Serializable, Permission
{
	static final long serialVersionUID = -5681890426894240536L;

	@Column(nullable = false, updatable = false)
	private MgtResource resource;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private ManagementAssignment level;

	public ManagementPermission(MgtResource resource, ManagementAssignment level)
	{
		Assert.noNullElements(new Object[] { resource, level });
		this.resource = resource;
		this.level = level;
	}

	@Override
	public boolean implies(Permission p)
	{
		return false;
	}
}