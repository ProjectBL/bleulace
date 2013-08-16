package com.bleulace.domain.crm.presentation;

import org.junit.Test;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.crm.command.AccountManager;
import com.bleulace.utils.AssertValid;
import com.bleulace.utils.dto.Mapper;

public class UserDTOMappingTest implements IntegrationTest
{
	@Test
	public void testMapping()
	{
		AssertValid.is(Mapper.map(new AccountManager().getAccount(),
				UserDTO.class));
	}
}