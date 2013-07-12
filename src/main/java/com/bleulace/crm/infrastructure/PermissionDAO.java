package com.bleulace.crm.infrastructure;

import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.springframework.data.domain.Persistable;

import com.bleulace.crm.domain.Account;

/**
 * A DAO to look up permissions for a specific {@link Account}.
 * 
 * @see JpaPermission
 * @author Arleigh Dickerson
 * 
 */
public interface PermissionDAO
{
	public Set<Permission> findByAccount(Account account);

	public Set<Permission> addPermissions(Account account,
			Persistable<?> target, String... actions);

	public Set<Permission> removePermissions(Account account,
			Persistable<?> target, String... actions);
}