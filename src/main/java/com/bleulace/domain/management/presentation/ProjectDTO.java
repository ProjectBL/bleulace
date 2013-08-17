package com.bleulace.domain.management.presentation;

import java.util.List;

public interface ProjectDTO extends ManageableResourceDTO
{
	public List<BundleDTO> getBundles();
}