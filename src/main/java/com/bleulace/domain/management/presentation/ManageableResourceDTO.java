package com.bleulace.domain.management.presentation;

import java.util.List;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;

public interface ManageableResourceDTO
{
	public String getId();

	public String getCaption();

	public String getDescription();

	public List<UserDTO> getManagers(ManagementLevel level);

	public Float getProgress();

	public boolean isComplete();
}
