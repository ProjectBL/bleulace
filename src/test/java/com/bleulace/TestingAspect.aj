package com.bleulace;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

aspect TestingAspect
{
	declare @type : UnitTest+ :@RunWith(BlockJUnit4ClassRunner.class);
	
	declare @type : ContextTest+ :@RunWith(SpringJUnit4ClassRunner.class);
	declare @type : ContextTest+ :@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml");
	declare @type : ContextTest+ : @ActiveProfiles("test");

	declare parents : IntegrationTest extends ContextTest;
	declare @type : IntegrationTest+ : @Transactional;
	declare @type : IntegrationTest+ :@TransactionConfiguration;
}