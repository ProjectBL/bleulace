package com.bleulace.domain.project;

import com.bleulace.domain.account.Account;

public class Fixtures
{
	public Project getProject()
	{
		Project project = new Project();
		project.setTitle("title");
		return project;
	}

	public Bundle getBundle()
	{
		Bundle b = new Bundle();
		b.setTitle("title");
		return b;
	}

	public Task getTask()
	{
		Task t = new Task();
		t.setTitle("title");
		return t;
	}

	public Account getAccount()
	{
		Account a = new Account();
		a.setEmail("foo@bar.com");
		a.setPassword("password");
		return a;
	}
}