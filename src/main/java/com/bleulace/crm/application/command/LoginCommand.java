package com.bleulace.crm.application.command;

/**
 * A command to log a user into the system.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class LoginCommand
{
	private String username;
	private char[] password;
	private boolean rememberMe;

	public LoginCommand()
	{
		setUsername("");
		setPassword("");
		setRememberMe(false);
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password.toString();
	}

	public void setPassword(String password)
	{
		this.password = password.toCharArray();
	}

	public boolean isRememberMe()
	{
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe)
	{
		this.rememberMe = rememberMe;
	}
}