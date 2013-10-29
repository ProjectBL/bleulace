package com.bleulace.domain.resource.infrastructure;

import org.springframework.stereotype.Service;

import com.bleulace.domain.resource.model.CompositeResource;

@Service
public interface ManagementService
{
	public void assignOwner(CompositeResource resource);
}