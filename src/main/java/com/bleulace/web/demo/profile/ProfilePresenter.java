package com.bleulace.web.demo.profile;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.Presenter;

@Presenter
class ProfilePresenter
{
	private Account account;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private ProfileView view;

	@Autowired
	private SystemUser user;

	void init(String accountId)
	{
		account = accountDAO.findOne(accountId);
		if (account == null)
		{
			throw new IllegalArgumentException();
		}
	}

	Account getAccount()
	{
		return account;
	}

	void resourceSelected(String id)
	{

	}
}