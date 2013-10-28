package com.bleulace.web.demo.avatar;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.web.BleulaceTheme;
import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.BleulaceTheme.AvatarSize;
import com.google.common.collect.ImmutableMap;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

@Component
class AvatarFactoryImpl implements AvatarFactory
{
	@Autowired
	private BleulaceTheme theme;

	//@formatter:off
	private final Map<AvatarSize, Integer> sizes = ImmutableMap.of(
			AvatarSize.MINI, 50, 
			AvatarSize.FULL,200
	);
	//@formatter:on

	AvatarFactoryImpl()
	{
		for (AvatarSize size : AvatarSize.values())
		{
			if (!sizes.containsKey(size))
			{
				throw new IllegalStateException();
			}
		}
	}

	@Override
	public Image make(AvatarGender gender, AvatarSize size)
	{
		Assert.notNull(gender);
		Assert.notNull(size);
		Image avatar = new Image("", new ThemeResource(
				theme.getAvatarLocation(gender)));
		int dim = sizes.get(size);
		avatar.setWidth(dim, Unit.PIXELS);
		avatar.setHeight(dim, Unit.PIXELS);
		return avatar;
	}
}