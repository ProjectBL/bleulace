package com.bleulace.crm.application.command.handler;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bleulace.crm.application.command.LoginCommand;

/**
 * Converts a login command to a Shiro-specific {@link AuthenticationToken}
 * implementation. Note that realms often expect certain subclasses of
 * {@link AuthenticationToken} and will throw exceptions if the provided token
 * is not an instance of the expected subclass.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component(UsernamePasswordLoginCommandConverter.NAME)
class UsernamePasswordLoginCommandConverter implements
		Converter<LoginCommand, AuthenticationToken>
{
	public static final String NAME = "usernamePasswordLoginCommandConverter";

	@Override
	public AuthenticationToken convert(LoginCommand source)
	{
		return new UsernamePasswordToken(source.getUsername(),
				source.getPassword(), source.isRememberMe());
	}
}