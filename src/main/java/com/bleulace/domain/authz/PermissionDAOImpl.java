package com.bleulace.domain.authz;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;

import com.bleulace.domain.account.Account;

@Component
class PermissionDAOImpl implements PermissionDAO
{
	@Override
	public Set<Permission> findByAccounts(Account account)
	{
		// TODO Auto-generated method stub
		return new HashSet<Permission>();
	}

	@Override
	public Set<Permission> addPermissions(Account account,
			Persistable<?> target, String... actions)
	{
		// TODO Auto-generated method stub
		return new HashSet<Permission>();
	}

	@Override
	public Set<Permission> removePermissions(Account account,
			Persistable<?> target, String... actions)
	{
		// TODO Auto-generated method stub
		return new HashSet<Permission>();
	}

}
