package com.bleulace.domain.management.presentation;

import com.bleulace.utils.dto.DTOFactory;
import com.bleulace.utils.dto.Factory;

@Factory(makes = BundleDTO.class)
class BundleDTOFactory implements DTOFactory<BundleDTO>
{
	@Override
	public BundleDTO make()
	{
		return new BundleDTOImpl();
	}
}
