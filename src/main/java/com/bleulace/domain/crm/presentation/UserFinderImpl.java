package com.bleulace.domain.crm.presentation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.QAccount;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.jpa.config.QueryFactory;
import com.bleulace.utils.dto.AbstractFinder;

@Component
class UserFinderImpl extends AbstractFinder<Account, UserDTO> implements
		UserFinder
{
	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private AccountDAO accountDAO;

	UserFinderImpl()
	{
		super(Account.class, UserDTO.class);
	}

	@Override
	public List<UserDTO> findFriends(String id)
	{
		return convert(accountDAO.findFriends(id));
	}

	@Override
	public List<UserDTO> findFriendRequests(String id)
	{
		return convert(accountDAO.findFriendRequests(id));
	}

	@Override
	public Map<UserDTO, ManagementLevel> findManagers(String id)
	{
		Map<UserDTO, ManagementLevel> map = new HashMap<UserDTO, ManagementLevel>();
		for (ManagementAssignment assignment : resourceDAO.findChildren(id,
				ManagementAssignment.class))
		{
			map.put(convert(assignment.getAccount()), assignment.getRole());
		}
		return map;
	}

	@Override
	public List<UserDTO> findBySearch(String searchTerm)
	{
		return convert(accountDAO.findBySearch(searchTerm));
	}

	@Override
	public List<UserDTO> findByIds(Collection<String> ids, boolean equals)
	{
		if (equals)
		{
			return convert(accountDAO.findAll(ids));
		}
		QAccount a = QAccount.account;
		return convert(QueryFactory.from(a).where(a.id.notIn(ids)).distinct()
				.list(a));
	}
}
