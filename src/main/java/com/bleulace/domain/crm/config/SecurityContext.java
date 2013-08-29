package com.bleulace.domain.crm.config;

import org.apache.shiro.subject.Subject;

public interface SecurityContext
{
	public Subject getSubject();
}