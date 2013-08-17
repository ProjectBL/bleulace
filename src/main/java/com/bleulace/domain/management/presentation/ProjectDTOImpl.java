package com.bleulace.domain.management.presentation;

import java.util.ArrayList;
import java.util.List;

import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.resource.model.Resource;
import com.bleulace.utils.dto.Mapper;

class ProjectDTOImpl extends ManageableResourceDTOImpl implements ProjectDTO
{
	private final List<BundleDTO> bundles = new ArrayList<BundleDTO>();

	@Override
	public List<BundleDTO> getBundles()
	{
		return bundles;
	}

	@Override
	public void setChildren(List<Resource> resources)
	{
		super.setChildren(resources);
		for (Resource resource : resources)
		{
			if (resource instanceof Bundle)
			{
				bundles.add(Mapper.map(resource, BundleDTO.class));
			}
		}
	}
}
