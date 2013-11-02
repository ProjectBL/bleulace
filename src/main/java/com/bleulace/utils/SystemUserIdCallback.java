package com.bleulace.utils;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.SystemUser;

abstract class SystemUserIdCallback implements IdCallback
{
	protected SystemUser getUser()
	{
		return SpringApplicationContext.getUser();
	}
}