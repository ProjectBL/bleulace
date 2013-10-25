package com.bleulace.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bleulace.utils.SystemProfiles;
import com.cybercom.vaadin.spring.UIScope;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class UIScopeConfig
{
	@Bean
	static UIScope uiScope()
	{
		return new UIScope();
	}
}
