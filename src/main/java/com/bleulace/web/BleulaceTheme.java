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
		FULL, MINI;
	}
}