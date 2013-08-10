package com.bleulace.crm.presentation;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.commenting.domain.Comment;
import com.bleulace.commenting.presentation.CommentDTO;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.domain.AccountDAO;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentDTOTest implements CommandGatewayAware
{
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CreateAccountCommand command;

	@Autowired
	private AccountDAO dao;

	@Test
	public void testCommentDTOMapping()
	{
		gateway().send(command);
		Comment comment = new Comment(dao.findOne(command.getId()), "",
				"Lorem ipsum dolor.");
		CommentDTO dto = mapper.map(comment, CommentDTO.class);
		Assert.assertNotNull(dto.getContent());

		AccountDTO accountDto = dto.getAuthor();
		Assert.assertNotNull(accountDto);
		Assert.assertNotNull(accountDto.getFirstName());
	}
}
