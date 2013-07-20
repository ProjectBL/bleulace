package com.bleulace.crm.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountDAO;
import com.bleulace.utils.dto.DTOConverter;

@Component
class AccountFinderImpl implements AccountFinder
{
	@Autowired
	private AccountDAO dao;

	@Autowired
	private DTOConverter<Account, AccountDTO> accountDTOConverter;

	@Override
	@Autowired
	public List<AccountDTO> findAll()
	{
		return accountDTOConverter.convert(dao.findAll());
	}

	@Override
	public AccountDTO findById(String id)
	{
		return accountDTOConverter.convert(dao.findOne(id));
	}

	@Override
	public List<AccountDTO> findFriends(String id)
	{
		Account account = dao.findOne(id);
		if (account != null)
		{
			return (List<AccountDTO>) accountDTOConverter.convert(account
					.getFriends());
		}
		return null;
	}
}