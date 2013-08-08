package com.bleulace.ui.web.profile;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.TabSheet;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configuration
class ProfileConfig
{
	static final String PROFILE_IMAGE_DATA = "profileImage";

	//@formatter:off
	private static final String[] TABSHEET_CAPTIONS = new String[] {
		"Status",
		"Image",
		"Playlist",
		"Location",
		"Event",
		"Pin",
		"Blog"
	};
	//@formatter:on

	private static final Integer IMAGE_WIDTH = 300;
	private static final Integer IMAGE_HEIGHT = 300;

	@Bean
	@Qualifier("profileTabSheet")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public TabSheet profileTabSheet()
	{
		TabSheet bean = new TabSheet();
		for (String caption : TABSHEET_CAPTIONS)
		{
			bean.addTab(new CustomComponent(), caption);
		}
		return bean;
	}

	/**
	 * @see ProfileImageContentAspect
	 */
	@Bean
	@Qualifier("profileImage")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Image profileImage()
	{
		Image bean = new Image();
		bean.setData(PROFILE_IMAGE_DATA);
		bean.setWidth(IMAGE_WIDTH + "px");
		bean.setHeight(IMAGE_HEIGHT + "px");
		bean.setSource(null);
		return bean;
	}

	// singleton
	@Bean
	@Qualifier("defaultProfileImageResource")
	public Resource defaultProfileImageResource()
	{
		return new ThemeResource("img/ProfilePlaceholder.png");
	}
}