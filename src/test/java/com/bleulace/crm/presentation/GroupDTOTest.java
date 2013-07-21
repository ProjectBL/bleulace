package com.bleulace.crm.presentation;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateGroupCommand;
import com.bleulace.crm.domain.GroupDAO;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupDTOTest implements CommandGatewayAware
{
	@Autowired
	private CreateGroupCommand createGroupCommand;

	@Autowired
	private GroupDAO groupDAO;

	@Test
	public void testGroupDTOFinder()
	{
		gateway().send(createGroupCommand);
		GroupDTO dto = GroupDTO.FINDER.findById(groupDAO.findAll().iterator()
				.next().getId());
		Assert.assertNotNull(dto);
		Assert.assertTrue(dto.getMembers().size() > 0);

		List<GroupDTO> dtos = GroupDTO.FINDER.findByMemberId(createGroupCommand
				.getCreatorId());
		Assert.assertTrue(dtos.size() > 0);
	}
}
