package com.bleulace.domain.management.presentation;

import java.util.List;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.model.Resource;

public interface ManageableResourceDTO
{
	public String getId();

	public void setId(String id);

	public String getCaption();

	public void setCaption(String caption);

	public String getDescription();

	public void setDescription(String description);

	public List<UserDTO> getManagers(ManagementLevel level);

	public void setChildren(List<Resource> children);
}
