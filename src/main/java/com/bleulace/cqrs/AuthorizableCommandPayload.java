package com.bleulace.cqrs;

import org.apache.shiro.authz.Permission;

public interface AuthorizableCommandPayload extends CommandPayload
{
	public Permission getPermission();
}
