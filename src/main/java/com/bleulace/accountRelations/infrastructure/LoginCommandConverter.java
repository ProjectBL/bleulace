package com.bleulace.accountRelations.infrastructure;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bleulace.accountRelations.application.command.LoginCommand;

@Component(LoginCommandConverter.NAME)
public class LoginCommandConverter implements
		Converter<LoginCommand, AuthenticationToken>
{
	public static final String NAME = "loginCommandConverter";

	@Override
	public AuthenticationToken convert(LoginCommand source)
	{
		return new UsernamePasswordToken(source.getUsername(),
				source.getPassword(), source.getRememberMe());
	}
}