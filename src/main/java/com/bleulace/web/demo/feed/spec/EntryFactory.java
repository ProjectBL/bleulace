package com.bleulace.web.demo.feed.spec;

import com.vaadin.ui.Component;

public interface EntryFactory<A, P>
{
	public Component make(Usage<A, P> data);

	public boolean matches(Usage<?, ?> data);
}