package com.bleulace.web.demo.calendar.appearance;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.web.SystemUser;

@Component
class StyleNameCallbackFactory implements FactoryBean<StyleNameCallback>
{
	@Autowired
	private SystemUser user;

	@Autowired
	private StyleNameCallback singleVieweeStrategy;

	@Autowired
	private StyleNameCallback multipleVieweeStrategy;

	private StyleNameCallbackFactory()
	{
	}

	@Override
	public StyleNameCallback getObject() throws Exception
	{
		return // user.getVieweeIds().size() < 2 ?
		singleVieweeStrategy
		// : multipleVieweeStrategy;
		;
	}

	@Override
	public Class<?> getObjectType()
	{
		return StyleNameCallback.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}