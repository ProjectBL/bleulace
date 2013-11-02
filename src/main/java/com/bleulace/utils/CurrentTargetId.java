package com.bleulace.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("targetId")
@Scope("prototype")
public final class CurrentTargetId extends SystemUserIdCallback
{
	@Override
	public String evaluate()
	{
		return getUser().getTarget();
	}
}