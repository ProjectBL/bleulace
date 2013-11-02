package com.bleulace.web;

import com.bleulace.domain.crm.Gender;

public interface BleulaceTheme
{
	public String getAvatarLocation(Gender gender);

	public String getThemeName();

	public enum CalendarColor
	{
		GREEN, BLUE, RED, ORANGE, DARKRED, GREY;

		public String getStyleName()
		{
			return name().toLowerCase();
		}
	}
}