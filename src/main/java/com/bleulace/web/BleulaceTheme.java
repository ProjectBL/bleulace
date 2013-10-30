package com.bleulace.web;

public interface BleulaceTheme
{
	public String getAvatarLocation(AvatarGender gender);

	public String getThemeName();

	public enum AvatarGender
	{
		MALE, FEMALE;
	}

	public enum AvatarSize
	{
		FULL, MEDIUM, MINI;
	}

	public enum CalendarColor
	{
		GREEN, BLUE, RED, ORANGE, DARKRED, GREY;

		public String getStyleName()
		{
			return name().toLowerCase();
		}
	}
}