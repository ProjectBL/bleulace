package com.bleulace.web;

import org.apache.shiro.subject.Subject;

public interface SecurityContext
{
	public Subject getSubject();
}