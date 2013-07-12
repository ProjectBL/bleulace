package com.bleulace.crm.application.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

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
public class ChangePasswordCommand
{
	@TargetAggregateIdentifier
	private String id;

	/**
	 * we storing value as char array becuse Strings, being immutable, have a
	 * tendency to show up in inconvenient locations in the event of a memory
	 * dump. We certainly do not want to expose our clients' login info in the
	 * event of a system failure.
	 * 
	 * I --THINK-- this is the safe way to do it.
	 */
	private char[] value;

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

	@Password
	public String getValue()
	{
		return value.toString();
	}

	public void setValue(String value)
	{
		this.value = value.toCharArray();
	}
}