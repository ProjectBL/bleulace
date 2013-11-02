package com.bleulace.web.demo;

import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.Gender;
import com.bleulace.web.BleulaceTheme;

@Component
class DemoTheme implements BleulaceTheme
{
	private static final String THEME_NAME = "bleulacetheme";

	private static final String AVATAR_LOCATION_MALE = "img/ProfilePlaceholderMale.png";

	private static final String AVATAR_LOCATION_FEMALE = "img/ProfilePlaceholderFemale.jpg";

	@Override
	public String getAvatarLocation(Gender gender)
	{
		switch (gender)
		{
		case MALE:
			return AVATAR_LOCATION_MALE;
		case FEMALE:
			return AVATAR_LOCATION_FEMALE;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getThemeName()
	{
		return THEME_NAME;
	}
}