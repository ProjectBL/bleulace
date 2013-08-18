package com.bleulace.domain.management.presentation;

import java.util.List;

public interface BundleFinder
{
	public List<BundleDTO> findByManager(String managerId);
}