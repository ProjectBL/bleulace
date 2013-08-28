package com.bleulace;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bleulace.utils.SystemProfiles;
import com.bleulace.web.SecurityContext;

@Component
@Profile(SystemProfiles.TEST)
class TestSecurityContext implements SecurityContext
{
	@Override
	@SuppressAjWarnings
	public Subject getSubject()
	{
		return SecurityUtils.getSubject();
	}

}
