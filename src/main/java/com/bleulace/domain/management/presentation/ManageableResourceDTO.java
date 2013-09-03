package com.bleulace.domain.management.presentation;

import java.util.Map;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.management.model.ManagementLevel;

public interface ManageableResourceDTO
{
	public String getId();

	@NotEmpty
	public String getCaption();

	@NotEmpty
	public String getDescription();

	public Map<UserDTO, ManagementLevel> getManagers();

	public Set<UserDTO> getManagers(ManagementLevel... levels);

	public Float getProgress();

	public boolean isComplete();
}
