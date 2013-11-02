package com.bleulace.web.demo.profile;

import java.util.Map.Entry;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

class KeyValueLabel extends Label
{
	private final String key;

	KeyValueLabel(String key, String value)
	{
		this.key = key;
		super.setCaption(key);
		setCaption(value);
		addStyleName(Reindeer.LABEL_H2);
	}

	KeyValueLabel(Entry<String, String> entry)
	{
		this(entry.getKey(), entry.getValue());
	}

	KeyValueLabel(String key)
	{
		this(key, "");
	}

	@Override
	public void setCaption(String caption)
	{
		super.setCaption(key + ": " + caption);
	}
}