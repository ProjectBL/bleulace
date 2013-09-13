package com.bleulace.domain.management.presentation;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.bleulace.domain.management.model.ManagementLevel;

public interface ManageableResourceDTO
{
	@NotNull
	public String getId();

	// @NotEmpty
	public String getCaption();

	// @NotEmpty
	public String getDescription();

	public Map<String, ManagementLevel> getManagers();

	public Float getProgress();

	public boolean isComplete();
}
