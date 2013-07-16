package com.bleulace.crm.application.command;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

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
		Assert.noNullElements(new Object[] { username, password, rememberMe });
		token = new UsernamePasswordToken(username, password, rememberMe);
	}

	@NotBlank
	public String getUsername()
	{
		return token.getUsername();
	}

	@NotBlank
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