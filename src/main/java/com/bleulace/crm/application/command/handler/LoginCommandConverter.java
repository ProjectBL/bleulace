package com.bleulace.crm.application.command.handler;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.core.convert.converter.Converter;

import com.bleulace.crm.application.command.LoginCommand;

/**
 * Implementors will convert a {@link LoginCommand} to some instance of a
 * subclass of {@link AuthenticationToken}.
 * 
 * @author Arleigh Dickerson
 * 
 */
interface LoginCommandConverter extends
		Converter<LoginCommand, AuthenticationToken>
{
}