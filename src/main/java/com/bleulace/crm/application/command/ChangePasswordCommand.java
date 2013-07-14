package com.bleulace.crm.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.infrastructure.Password;

/**
 * A command to change an account's password
 * 
 * Hashing and Salting are taken care of by the annotated command handler on
 * {@link Account}
 * 
 * @author Arleigh Dickerson
 * 
 */
@RooJavaBean
public class ChangePasswordCommand
{
	@TargetAggregateIdentifier
	private final String id;

	@Password
	private String value = "";

	/**
	 * 
	 * @param id
	 *            , the id of the targeted account
	 * 
	 * @param value
	 *            , the password value to initalize with
	 */
	public ChangePasswordCommand(String id, String value)
	{
		this.id = id;
		setValue(value);
	}

	public String getId()
	{
		return id;
	}
}