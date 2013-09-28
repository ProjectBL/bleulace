package com.bleulace.web.main;

import com.bleulace.domain.resource.model.Resource;
import com.vaadin.ui.Component;

public class ResourceContentFactory
{
	public static Component make(Resource resource)
	{
		return new ResourceContent();
	}
}