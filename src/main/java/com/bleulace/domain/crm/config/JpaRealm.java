package com.bleulace.domain.crm.config;

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

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.JpaPermission;
import com.bleulace.utils.ctx.SpringApplicationContext;

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
		String accountId = (String) principalCollection.getPrimaryPrincipal();
		if (accountId != null)
		{
			Account account = getDAO().findOne(accountId);
			if (account != null)
			{
				// TODO : add permissions
			}
		}
		return info;
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