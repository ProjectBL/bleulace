package com.bleulace.mgt.presentation;

import java.util.List;

import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.utils.dto.Finder;

public interface BundleFinder extends Finder<BundleDTO>
{
	public List<BundleDTO> findByParentId(String parentId);

	public List<BundleDTO> findByAssignment(String accountId,
			ManagementAssignment assignment);
}