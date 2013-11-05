package com.bleulace.web;

import com.bleulace.utils.IdCallback;

public interface SystemUser extends IdCallback
{
	public String getId();

	public String getTarget();

	public void setTarget(String target);
	
	static aspect Impl
	{
		public String SystemUser.evaluate()
		{
			return this.getId();
		}
	}
}