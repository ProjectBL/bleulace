package com.bleulace.crm.application.event;

import org.modelmapper.ModelMapper;

import com.bleulace.crm.infrastructure.Encryptor;

/**
 * This event is used to track changes to an account's password.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class PasswordChangedEvent
{
	private byte[] hash;
	private byte[] salt;

	public PasswordChangedEvent(String newValue)
	{
		Encryptor encryptor = new Encryptor(newValue.toCharArray());
		new ModelMapper().map(encryptor, this);
	}

	public byte[] getHash()
	{
		return hash;
	}

	public byte[] getSalt()
	{
		return salt;
	}
}
