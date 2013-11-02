package com.bleulace.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("userId")
@Scope("prototype")
public final class CurrentUserId extends SystemUserIdCallback
{
	@Override
	public String evaluate()
	{
		return getUser().getId();
	}
}