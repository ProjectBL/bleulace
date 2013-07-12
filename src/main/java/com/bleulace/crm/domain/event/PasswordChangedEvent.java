package com.bleulace.crm.domain.event;

import com.bleulace.crm.infrastructure.Encryptor;

/**
 * This event is used to track changes to an account's password.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class PasswordChangedEvent
{
	private static final long serialVersionUID = 6321989607384240562L;

	private final Encryptor encryptor;

	public PasswordChangedEvent(String newPassword)
	{
		encryptor = new Encryptor(newPassword.toCharArray());
	}

	public byte[] getHash()
	{
		return encryptor.getHash();
	}

	public byte[] getSalt()
	{
		return encryptor.getSalt();
	}
}
