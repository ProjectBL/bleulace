package com.bleulace.web.demo.feed.spec;

import java.util.Date;

import com.bleulace.web.BleulaceTheme.AvatarGender;

public interface Usage<A, P>
{
	public MetaData getMetaData();

	public A getAction();

	public P getPredicate();

	public interface MetaData
	{
		public String getSubjectName();

		public AvatarGender getGender();

		public Date getTimestamp();
	}
}