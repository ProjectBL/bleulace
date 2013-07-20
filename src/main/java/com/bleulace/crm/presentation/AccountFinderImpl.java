package com.bleulace.crm.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountDAO;
import com.bleulace.utils.dto.BasicFinder;

@Component
class AccountFinderImpl extends BasicFinder<Account, AccountDTO> implements
		AccountFinder
{
	@Autowired
	private AccountDAO dao;

	public AccountFinderImpl()
	{
		super(Account.class, AccountDTO.class);
	}

	@Override
	public List<AccountDTO> findFriends(String id)
	{
		Account account = dao.findOne(id);
		if (account != null)
		{
			return (List<AccountDTO>) getConverter().convert(
					account.getFriends());
		}
		return null;
	}
}