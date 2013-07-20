package com.bleulace.crm.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.crm.domain.AccountGroup;
import com.bleulace.crm.domain.GroupDAO;
import com.bleulace.utils.dto.DTOConverter;

@Component
class GroupFinderImpl implements GroupFinder
{
	@Autowired
	private GroupDAO dao;

	@Autowired
	private DTOConverter<AccountGroup, GroupDTO> groupDTOConverter;

	@Override
	public GroupDTO findById(String id)
	{
		return groupDTOConverter.convert(dao.findOne(id));
	}

	@Override
	public GroupDTO findOneByTitle(String title)
	{
		return groupDTOConverter.convert(dao.findOneByTitle(title));
	}

	@Override
	public List<GroupDTO> findByTitle(String title)
	{
		return groupDTOConverter.convert(dao.findByTitle(title));
	}

	@Override
	public List<GroupDTO> findByMemberId(String memberId)
	{
		return groupDTOConverter.convert(dao.findByMemberId(memberId));
	}

	@Override
	public List<GroupDTO> findAll()
	{
		return groupDTOConverter.convert(dao.findAll());
	}
}
