package com.bleulace.utils;

import org.springframework.core.type.filter.AssignableTypeFilter;

import com.vaadin.navigator.View;

public class ViewResolvingClasspathScanner extends ComponentClassScanner
{
	public ViewResolvingClasspathScanner()
	{
		super();
		addIncludeFilter(new AssignableTypeFilter(View.class));
	}
}
