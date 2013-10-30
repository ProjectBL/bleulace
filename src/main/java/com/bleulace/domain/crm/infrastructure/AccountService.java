package com.bleulace.domain.crm.infrastructure;

public interface AccountService
{
	public void login(String username, String password, boolean rememberMe);

	public void logout();
}