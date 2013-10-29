package com.bleulace.domain.crm.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

@Service
class ShiroAccountService implements AccountService
{
	@Override
	public void login(String username, String password, boolean rememberMe)
	{
		SecurityUtils.getSubject().login(
				new UsernamePasswordToken(username, password.toCharArray(),
						rememberMe));
	}

	@Override
	public void logout()
	{
		SecurityUtils.getSubject().logout();
	}
}