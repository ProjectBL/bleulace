package com.bleulace.crm.presentation;

import java.util.List;

import com.bleulace.utils.dto.Finder;

public interface AccountFinder extends Finder<AccountDTO>
{
	public List<AccountDTO> findFriends(String id);
}