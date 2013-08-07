package com.bleulace.ui.web.profile;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.server.Resource;
import com.vaadin.ui.Image;

/**
 * @author Arleigh Dickerson
 * 
 */
aspect DefaultImageContentAspect
{
	pointcut onSetImageSource(Image image, Resource source) : 
		call(public void Image.setSource(Resource))
		&& target(image) 
		&& args(source) 
		&& within(com.bleulace.ui.web.profile..*);

	void around(Image image, Resource source) : onSetImageSource(image,source)
	{
		if (source == null && isProfileImage(image))
		{
			source = SpringApplicationContext.get().getBean(
					"defaultProfileImageResource", Resource.class);
		}
		proceed(image, source);
	}

	private boolean isProfileImage(Image image)
	{
		return ((String) image.getData())
				.equals(ProfileConfig.PROFILE_IMAGE_DATA);
	}
}
