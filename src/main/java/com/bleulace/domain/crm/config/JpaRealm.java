package com.bleulace.domain.crm.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.HashedPassword;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.jpa.config.QueryFactory;
import com.bleulace.utils.ctx.SpringApplicationContext;

/**
 * A realm which performs authentication and authorization for {@link Account}
 * instances.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class JpaRealm extends AuthorizingRealm
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException
	{
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		if (token == null)
		{
			return null;
		}

		String username = usernamePasswordToken.getUsername();
		if (username == null)
		{
			return null;
		}

		Account identity = getDAO().findByUsername(username);
		if (identity == null)
		{
			return null;
		}

		HashedPassword password = identity.getPassword();
		return new SimpleAuthenticationInfo(identity.getId(),
				password.getHash(), ByteSource.Util.bytes(password.getSalt()),
				getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection)
	{
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		String accountId = (String) principalCollection.getPrimaryPrincipal();
		if (accountId != null)
		{
			info.addObjectPermissions(getManagementPermissions(accountId));
			info.addStringPermissions(getCalendarPermissions(accountId));
		}
		return info;
	}

	private Collection<String> getCalendarPermissions(String accountId)
	{
		List<String> ids = new ArrayList<String>();
		ids.add(accountId);
		ids.addAll(getDAO().findFriendIds(accountId));

		List<String> permissions = new ArrayList<String>();
		permissions.add("calendar:update,delete:" + accountId);
		for (String id : ids)
		{
			permissions.add("calendar:create,read:" + id);
		}
		return permissions;
	}

	private Collection<Permission> getManagementPermissions(String accountId)
	{
		QManagementAssignment a = new QManagementAssignment("a");

		List<Permission> permissions = new LinkedList<Permission>();
		for (ManagementAssignment assignment : QueryFactory.from(a)
				.where(a.account.id.eq(accountId)).list(a))
		{
			permissions.add(assignment.getRole().on(
					assignment.getResource().getId()));
		}

		return permissions;
	}

	/**
	 * 
	 * @return an accountDAO
	 */
	private AccountDAO getDAO()
	{
		return SpringApplicationContext.get().getBean(AccountDAO.class);
	}
}