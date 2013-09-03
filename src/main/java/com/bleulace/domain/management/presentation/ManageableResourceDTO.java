package com.bleulace.domain.management.presentation;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.domain.management.model.ManagementLevel;

public interface ManageableResourceDTO
{
	public String getId();

	@NotEmpty
	public String getCaption();

	@NotEmpty
	public String getDescription();

	public Map<String, ManagementLevel> getManagers();

	public Float getProgress();

	public boolean isComplete();
}
