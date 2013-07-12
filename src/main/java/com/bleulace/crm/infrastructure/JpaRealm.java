package com.bleulace.crm.infrastructure;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountFinder;

/**
 * A realm which performs authentication and authorization for {@link Account}
 * instances.
 * 
 * @see JpaPermission
 * @see PermissionDAO
 * @see JpaPermissionDAO
 * 
 * @author Arleigh Dickerson
 * 
 */
public class JpaRealm extends AuthorizingRealm
{
	@Autowired
	private PermissionDAO permissionDAO;

	@Autowired
	private AccountFinder finder;

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

		Account identity = finder.findByEmail(username);
		if (identity == null)
		{
			return null;
		}

		return new SimpleAuthenticationInfo(identity.getIdentifier(),
				identity.getHash(), ByteSource.Util.bytes(identity.getSalt()),
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
		String id = (String) principalCollection.getPrimaryPrincipal();
		if (id != null)
		{
			Account account = finder.findById(id);
			info.addObjectPermissions(permissionDAO.findByAccount(account));
		}
		return info;
	}
}