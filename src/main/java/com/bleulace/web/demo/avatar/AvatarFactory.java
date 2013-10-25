package com.bleulace.web.demo.avatar;

import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.vaadin.ui.Image;

public interface AvatarFactory
{
	public Image make(AvatarGender gender, AvatarSize size);
}