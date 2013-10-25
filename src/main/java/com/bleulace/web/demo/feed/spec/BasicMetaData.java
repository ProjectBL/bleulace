package com.bleulace.web.demo.feed.spec;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.web.BleulaceTheme.AvatarGender;
import com.bleulace.web.demo.feed.spec.Usage.MetaData;

@RooJavaBean
public class BasicMetaData implements MetaData
{
	private String subjectName;
	private AvatarGender gender;
	private Date timestamp = new Date();

	public void valid()
	{
		Assert.notNull(subjectName);
		Assert.notNull(gender);
		Assert.isTrue(!subjectName.equals(""));
	}
}