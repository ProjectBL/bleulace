package com.bleulace.domain.management.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.utils.dto.AbstractFinder;

class BundleFinderImpl extends AbstractFinder<Bundle, BundleDTO> implements
		BundleFinder
{
	public BundleFinderImpl()
	{
		super(Bundle.class, BundleDTO.class);
	}

	@Autowired
	private ResourceDAO dao;

	@Override
	public List<BundleDTO> findByManager(String managerId)
	{
		return convert(dao.findByManager(managerId, Bundle.class));
	}

}
