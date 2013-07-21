package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.mgt.domain.Bundle;
import com.bleulace.mgt.domain.BundleDAO;
import com.bleulace.mgt.domain.ManagementAssignment;
import com.bleulace.mgt.domain.ProjectDAO;
import com.bleulace.utils.dto.BasicFinder;

@Component
public class BundleFinderImpl extends BasicFinder<Bundle, BundleDTO> implements
		BundleFinder
{
	@Autowired
	private ProjectDAO projectDAO;

	@Autowired
	private BundleDAO bundleDAO;

	public BundleFinderImpl()
	{
		super(Bundle.class, BundleDTO.class);
	}

	@Override
	public List<BundleDTO> findByAssignment(String accountId,
			ManagementAssignment assignment)
	{
		return getConverter().convert(
				projectDAO.findByAssignment(accountId, assignment,
				// Arrays.asList(Bundle.class)));
						Bundle.class));
	}

	@Override
	public List<BundleDTO> findByParentId(String parentId)
	{
		return getConverter().convert(bundleDAO.findByParentId(parentId));
	}
}