package com.bleulace.crm.infrastructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.infrastructure.QueryFactory;

/**
 * An implementation of {@link PermissionDAO}
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
class JpaPermissionDAO implements PermissionDAO
{
	/**
	 * Find the authorization space for a single account.
	 * 
	 * @param the
	 *            account owning the permission set to be retrieved.
	 */
	@Override
	public Set<Permission> findByAccount(Account account)
	{
		QJPAPermission p = QJPAPermission.jPAPermission;
		List<JpaPermission> currentPermissions = QueryFactory.from(p)
				.where(p.account.eq(account)).list(p);
		return new HashSet<Permission>(currentPermissions);
	}

	/**
	 * Attempt to assemble and add permissions for the following actions on the
	 * following target for the following user.
	 * 
	 * If account already has authorization on the assembled permission space,
	 * do nothing.
	 * 
	 * Otherwise, add the permission.
	 * 
	 * @return the set of all permissions which were newly added to the account.
	 * 
	 */
	@Override
	public Set<Permission> addPermissions(Account account,
			Persistable<?> target, String... actions)
	{
		JpaPermission toAdd = assemblePermission(account, target, actions);

		Set<Permission> permissions = new HashSet<Permission>();

		if (!SecurityUtils.getSecurityManager().isPermitted(
				account.getPrincipalCollection(), toAdd))
		{
			account.getPermissions().add(toAdd);
			permissions.add(toAdd);
		}

		return permissions;
	}

	/**
	 * Attempt to assemble and remove permissions for the following actions on
	 * the following target for the following user.
	 * 
	 * If authorization for the given account has already been revoked or does
	 * not exist, do nothing do nothing.
	 * 
	 * Otherwise, remove the permission.
	 * 
	 * @return the set of all permissions which were revoked from the account.
	 * 
	 */
	@Override
	public Set<Permission> removePermissions(Account account,
			Persistable<?> target, String... actions)
	{

		Set<Permission> removedPermissions = new HashSet<Permission>();
		Permission toRemove = assemblePermission(account, target, actions);
		for (Permission permission : findByAccount(account))
		{
			if (toRemove.implies(permission))
			{
				account.getPermissions().remove(permission);
				removedPermissions.add(permission);
			}
		}
		return removedPermissions;
	}

	private JpaPermission assemblePermission(Account account,
			Persistable<?> target, String... actions)
	{
		String domain = target.getClass().getSimpleName();

		Set<String> actionSet = new HashSet<String>();
		for (String action : actions)
		{
			actionSet.add(action);
		}

		Set<String> targetSet = new HashSet<String>();
		targetSet.add(target.getId().toString());

		return new JpaPermission(account, domain, actionSet, targetSet);
	}
}
