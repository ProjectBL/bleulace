package com.bleulace.security;

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

import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.CalendarEntryParticipant;
import com.frugalu.api.messaging.jpa.EntityManagerReference;

public class JpaRealm extends AuthorizingRealm
{
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

		Account identity = Account.findByEmail(username);
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
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Account account = EntityManagerReference.get().getReference(
				Account.class, principalCollection.getPrimaryPrincipal());
		for (CalendarEntryParticipant participation : CalendarEntryParticipant
				.findByAccounts(account))
		{
			String permissionString = getPermission(participation);
			if (participation != null)
			{
				info.addStringPermission(permissionString);
			}
		}
		return info;
	}

	private String getPermission(CalendarEntryParticipant participation)
	{
		switch (participation.getStatus())
		{
		case HOST:
		case ACCEPTED:
		case DECLINED:
		case PENDING:
		default:
		}
		return null;
	}
}