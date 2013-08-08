package com.bleulace.crm.presentation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.PostStatusUpdateCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountDTOTest implements CommandGatewayAware
{
	@Autowired
	private PostStatusUpdateCommand postStatusUpdateCommand;

	@Test
	public void testAccountDTO()
	{
		gateway().send(postStatusUpdateCommand);
		AccountDTO dto = AccountDTO.FINDER.findById(postStatusUpdateCommand
				.getAccountId());
		Assert.assertNotNull(dto);
		Assert.assertTrue(dto.getStatusUpdates().size() > 0);
	}
}