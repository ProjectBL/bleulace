package com.bleulace.domain.crm.presentation;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.crm.command.CrmCommandFactory;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.AssertValid;
import com.bleulace.utils.Locator;
import com.bleulace.utils.dto.Mapper;

public class UserDTOMappingTest implements IntegrationTest
{
	@Autowired
	private CrmCommandFactory factory;

	private UserDTO dto;

	@Autowired
	private Validator validator;

	@Before
	public void setUpAccount()
	{
		factory.createAccount();
		dto = createDTO();
	}

	@Test
	public void testMapping()
	{
		AssertValid.is(createDTO());
	}

	private UserDTO createDTO()
	{
		return Mapper.map(Locator.locate(Account.class), UserDTO.class);
	}
}
