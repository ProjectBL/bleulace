package com.bleulace.domain.authz;

import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.springframework.data.domain.Persistable;

import com.bleulace.domain.account.Account;

public interface PermissionDAO
{
	public Set<Permission> findByAccounts(Account account);

	public Set<Permission> addPermissions(Account account,
			Persistable<?> target, String... actions);

	public Set<Permission> removePermissions(Account account,
			Persistable<?> target, String... actions);
}