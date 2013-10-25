package com.bleulace.web.demo.feed.spec;

import com.vaadin.ui.Component;

public interface UsageConverter
{
	public <A, P> Component convert(Usage<A, P> usage);
}