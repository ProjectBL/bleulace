package com.bleulace.crm.domain.event;

import java.io.Serializable;

import com.bleulace.crm.infrastructure.Encryptor;

/**
 * This event is used to track changes to an account's password.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class PasswordChangedEvent implements Serializable
{
	private static final long serialVersionUID = 6321989607384240562L;

	private byte[] hash;
	private byte[] salt;

	public PasswordChangedEvent(String newPassword)
	{
		Encryptor encryptor = new Encryptor(newPassword.toCharArray());
		hash = encryptor.getHash();
		salt = encryptor.getSalt();
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
