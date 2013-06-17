package net.bluelace.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bluelace.domain.account.Account;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class JpaRealm extends AuthorizingRealm
{
	@PersistenceContext
	private EntityManager entityManager;

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
		Account identity = entityManager.find(Account.class, username);
		if (identity == null)
		{
			return null;
		}
		return new SimpleAuthenticationInfo(identity.getId(),
				identity.getHash(), ByteSource.Util.bytes(identity.getSalt()),
				getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection)
	{
		// TODO Auto-generated method stub
		return null;
	}
}