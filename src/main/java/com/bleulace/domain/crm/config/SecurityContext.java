package com.bleulace.domain.crm.config;

import org.apache.shiro.subject.Subject;

interface SecurityContext
{
	public Subject getSubject();
}