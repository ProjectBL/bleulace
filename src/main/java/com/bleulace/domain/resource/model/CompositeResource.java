package com.bleulace.domain.resource.model;

import java.util.List;

public interface CompositeResource extends Resource
{
	void addChild(Resource child);

	void removeChild(Resource child);

	boolean isCompatible(Resource child);

	List<? extends Resource> getChildren();
}