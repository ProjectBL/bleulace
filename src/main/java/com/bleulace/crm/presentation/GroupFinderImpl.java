package com.bleulace.crm.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.AccountGroup;
import com.bleulace.crm.domain.GroupDAO;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class GroupFinderImpl extends BasicFinder<AccountGroup, GroupDTO>
		implements GroupFinder
{
	@Autowired
	private GroupDAO dao;

	public GroupFinderImpl()
	{
		super(AccountGroup.class, GroupDTO.class);
	}

	@Override
	public GroupDTO findOneByTitle(String title)
	{
		return getConverter().convert(dao.findOneByTitle(title));
	}

	@Override
	public List<GroupDTO> findByTitle(String title)
	{
		return getConverter().convert(dao.findByTitle(title));
	}

	@Override
	public List<GroupDTO> findByMemberId(String memberId)
	{
		return getConverter().convert(dao.findByMemberId(memberId));
	}

	@Override
	public List<GroupDTO> findAll()
	{
		return getConverter().convert(dao.findAll());
	}
}
