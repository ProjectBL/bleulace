package com.bleulace.crm.infrastructure;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashRequest.Builder;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

/**
 * Encrypts and salts a message represented as a character array
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configurable(preConstruction = true)
public class Encryptor
{
	private final byte[] hash;
	private final byte[] salt;

	@Autowired
	private Builder hashRequestBuilder;

	@Autowired
	private HashService hashService;

	@Autowired
	private RandomNumberGenerator randomNumberGenerator;

	/**
	 * 
	 * @param source
	 *            the message to encrypt
	 */
	public Encryptor(char[] source)
	{
		Assert.notNull(source);
		ByteSource salt = randomNumberGenerator.nextBytes();
		Hash hash = calculateHash(ByteSource.Util.bytes(source), salt);
		this.hash = hash.getBytes();
		this.salt = hash.getSalt().getBytes();
	}

	public byte[] getHash()
	{
		return hash;
	}

	public byte[] getSalt()
	{
		return salt;
	}

	private Hash calculateHash(ByteSource source, ByteSource salt)
	{
		hashRequestBuilder.setSalt(salt);
		hashRequestBuilder.setSource(source);
		HashRequest hashrequest = hashRequestBuilder.build();
		return hashService.computeHash(hashrequest);
	}
}