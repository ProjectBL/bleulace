package com.bleulace.domain.management.model;

import org.apache.shiro.authz.Permission;
import org.junit.Test;

import com.bleulace.UnitTest;

public class ManagementLevelPermissionUnitTest implements UnitTest
{
	Permission current;

	@Test
	public void testLoop()
	{
		current = ManagementLevel.LOOP;
		assertImplies(ManagementLevel.LOOP, true);
		assertImplies(ManagementLevel.MIX, false);
		assertImplies(ManagementLevel.OWN, false);
	}

	@Test
	public void testMix()
	{
		current = ManagementLevel.MIX;
		assertImplies(ManagementLevel.LOOP, true);
		assertImplies(ManagementLevel.MIX, true);
		assertImplies(ManagementLevel.OWN, false);
	}

	@Test
	public void testOwn()
	{
		current = ManagementLevel.OWN;
		assertImplies(ManagementLevel.LOOP, true);
		assertImplies(ManagementLevel.MIX, true);
		assertImplies(ManagementLevel.OWN, true);
	}

	private void assertImplies(Permission p, boolean value)
	{
		org.junit.Assert.assertTrue(value == current.implies(p));
	}
}
