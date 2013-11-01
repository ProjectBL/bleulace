package com.bleulace.web.demo.profile;

import com.vaadin.ui.Label;

class KeyValueLabel extends Label
{
	private final String key;

	KeyValueLabel(String key)
	{
		this.key = key;
		super.setCaption(key);
	}

	@Override
	public void setCaption(String caption)
	{
		super.setCaption(key + ": " + caption);
	}
}