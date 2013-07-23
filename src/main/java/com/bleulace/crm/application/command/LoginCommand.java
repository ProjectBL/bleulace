package com.bleulace.crm.application.command;

import javax.validation.constraints.NotNull;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.util.Assert;

/**
 * A command to authenticate and log in a system user.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class LoginCommand
{
	private UsernamePasswordToken token = new UsernamePasswordToken("", "",
			false);

	public LoginCommand()
	{
	}

	public LoginCommand(String username, String password)
	{
		this(username, password, false);
	}

	public LoginCommand(String username, String password, boolean rememberMe)
	{
		token = new UsernamePasswordToken(username, password, rememberMe);
	}

	@NotNull
	public String getUsername()
	{
		return token.getUsername();
	}

	@NotNull
	public String getPassword()
	{
		return token.getPassword().toString();
	}

	public void setUsername(String username)
	{
		token.setUsername(username);
	}

	public void setPassword(String password)
	{
		token.setPassword(password.toCharArray());
	}

	public boolean isRememberMe()
	{
		return token.isRememberMe();
	}

	public void setRememberMe(boolean rememberMe)
	{
		token.setRememberMe(rememberMe);
	}

	public AuthenticationToken getToken()
	{
		return token;
	}
}