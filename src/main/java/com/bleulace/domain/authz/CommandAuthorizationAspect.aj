package com.bleulace.domain.authz;

import com.bleulace.domain.Command;

public aspect CommandAuthorizationAspect
{
	declare @method : public void Command+.execute() : @RequiresPermissionValues;
}