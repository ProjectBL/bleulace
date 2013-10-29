package com.bleulace.domain.resource.infrastructure;

import org.springframework.stereotype.Service;

import com.bleulace.domain.resource.model.Resource;

@Service
public interface ManagementService
{
	public void assignOwner(Resource resource);
}
