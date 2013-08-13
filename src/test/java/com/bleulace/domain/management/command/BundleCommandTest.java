package com.bleulace.domain.management.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.management.model.Bundle;
import com.bleulace.utils.Locator;

public abstract class BundleCommandTest extends ProjectCommandTest
{
	@Autowired
	private ManagementCommandFactory factory;

	@Before
	public void createBundle()
	{
		factory.createBundle(getProject().getId());
	}

	@Test
	public void createdBundleExistsInDb()
	{
		Assert.assertNotNull(getBundle());
	}

	public Bundle getBundle()
	{
		return Locator.locate(Bundle.class);
	}
}