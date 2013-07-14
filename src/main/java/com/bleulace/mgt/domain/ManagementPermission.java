package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.shiro.authz.Permission;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@RooJavaBean(settersByDefault = false)
@Embeddable
public class ManagementPermission implements Serializable, Permission
{
	static final long serialVersionUID = -5681890426894240536L;

	@Column(nullable = false, updatable = false)
	private String targetId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private ManagementLevel level;

	@SuppressWarnings("unused")
	private ManagementPermission()
	{
	}

	public ManagementPermission(String targetId, ManagementLevel level)
	{
		Assert.noNullElements(new Object[] { targetId, level });
		this.targetId = targetId;
		this.level = level;
	}

	public ManagementPermission(String permissionString)
	{
		Assert.notNull(permissionString);
		String[] strs = permissionString.split(":");
		Assert.isTrue(strs.length == 2);
		targetId = strs[0];
		level = ManagementLevel.valueOf(strs[1]);
	}

	@Override
	public String toString()
	{
		return targetId + ":" + level;
	}

	@Override
	public boolean implies(Permission p)
	{
		Assert.notNull(p);
		if (this == p)
		{
			return true;
		}
		if (!(p instanceof ManagementPermission))
		{
			return false;
		}

		ManagementPermission other = (ManagementPermission) p;
		if (!level.implies(other.level))
		{
			return false;
		}
		if (targetId == null)
		{
			if (other.targetId != null)
			{
				return false;
			}
		}
		else if (!targetId.equals(other.targetId))
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result
				+ ((targetId == null) ? 0 : targetId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ManagementPermission other = (ManagementPermission) obj;
		if (level != other.level)
		{
			return false;
		}
		if (targetId == null)
		{
			if (other.targetId != null)
			{
				return false;
			}
		}
		else if (!targetId.equals(other.targetId))
		{
			return false;
		}
		return true;
	}
}