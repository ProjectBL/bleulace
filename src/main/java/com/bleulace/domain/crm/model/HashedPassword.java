package com.bleulace.domain.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashRequest.Builder;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean(settersByDefault = false)
@Configurable(preConstruction = true)
public class HashedPassword implements Serializable
{
	@Transient
	@Autowired
	private transient Builder hashRequestBuilder;

	@Transient
	@Autowired
	private transient HashService hashService;

	@Transient
	@Autowired
	private transient RandomNumberGenerator randomNumberGenerator;

	private byte[] hash = null;
	private byte[] salt = null;

	HashedPassword()
	{
		setNull();
	}

	HashedPassword(String value)
	{
		if (value == null)
		{
			setNull();
			return;
		}
		initialize(value);
	}

	private void setNull()
	{
		setValues(null, null);
	}

	private void setValues(byte[] hash, byte[] salt)
	{
		this.hash = hash;
		this.salt = salt;
	}

	private void initialize(String value)
	{
		Hash hash = calculateHash(ByteSource.Util.bytes(value.toCharArray()),
				randomNumberGenerator.nextBytes());
		setValues(hash.getBytes(), hash.getSalt().getBytes());
	}

	private Hash calculateHash(ByteSource source, ByteSource salt)
	{
		hashRequestBuilder.setSalt(salt);
		hashRequestBuilder.setSource(source);
		HashRequest hashrequest = hashRequestBuilder.build();
		return hashService.computeHash(hashrequest);
	}
}
