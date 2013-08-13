package com.bleulace;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

aspect TestingAspect
{
	declare parents : UnitTest extends BasicTest;
	declare parents : IntegrationTest extends UnitTest;

	declare @type : BasicTest+ :@RunWith(SpringJUnit4ClassRunner.class);
	declare @type : UnitTest+ :@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml");
	declare @type : UnitTest+ : @ActiveProfiles("test");
	declare @type : IntegrationTest+ : @Transactional;
	declare @type : IntegrationTest+ :@TransactionConfiguration;
}