package net.bluelace.domain.project;

import net.bluelace.domain.account.Account;

import org.apache.commons.lang3.RandomStringUtils;

public class Fixtures
{
	public Project getProject()
	{
		Project project = new Project();
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
		a.setId(RandomStringUtils.randomAlphabetic(5) + "@foo.com");
		a.setPassword("password");
		return a;
	}
}
