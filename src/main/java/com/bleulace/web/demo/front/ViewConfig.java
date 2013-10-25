package com.bleulace.web.demo.front;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bleulace.utils.SystemProfiles;

@Configuration
@Profile({ SystemProfiles.DEV, SystemProfiles.PROD })
class ViewConfig
{
}