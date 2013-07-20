package com.bleulace.crm.presentation;

import java.util.List;

import com.bleulace.utils.dto.Finder;

public interface GroupFinder extends Finder<GroupDTO>
{
	public GroupDTO findOneByTitle(String title);

	public List<GroupDTO> findByTitle(String title);

	public List<GroupDTO> findByMemberId(String memberId);
}