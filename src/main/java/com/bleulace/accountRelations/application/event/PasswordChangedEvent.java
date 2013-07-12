package com.bleulace.accountRelations.application.event;

import org.modelmapper.ModelMapper;

import com.bleulace.accountRelations.infrastructure.Encryptor;

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
