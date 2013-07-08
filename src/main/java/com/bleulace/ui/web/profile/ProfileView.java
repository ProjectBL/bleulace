package com.bleulace.ui.web.profile;

import com.vaadin.navigator.View;

public interface ProfileView extends View
{
	public void addListener(ProfileViewListener listener);

	public interface ProfileViewListener
	{
	}
}