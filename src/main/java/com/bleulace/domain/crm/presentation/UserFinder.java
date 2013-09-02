package com.bleulace.domain.crm.presentation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.utils.dto.Finder;

public interface UserFinder extends Finder<UserDTO>
{
	public List<UserDTO> findBySearch(String searchTerm);

	public List<UserDTO> findByIds(Collection<String> ids, boolean equals);

	public List<UserDTO> findFriends(String id);

	public List<UserDTO> findFriendRequests(String id);

	public Map<UserDTO, ManagementLevel> findManagers(String id);
}