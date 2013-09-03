package com.bleulace.domain.management.model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.Send;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.CrmCommandFactory;
import com.bleulace.domain.management.command.AssignManagersCommand;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.resource.model.ResourcePermission;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.Locator;

public class ManagementLevelPermissionIntegrationTest implements
		IntegrationTest, CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory mgtFactory;

	@Autowired
	private CrmCommandFactory crmFactory;

	String resourceId;

	String username;

	@Before
	public void createResources()
	{
		username = crmFactory.createAccount().getUsername();

		mgtFactory.createProject().getId();
		resourceId = Locator.locate(Project.class).getId();
	}

	@Test
	public void testEnumOn()
	{
		Permission p = ManagementLevel.OWN.on(resourceId);
		Permission q = new ResourcePermission(resourceId);
		Assert.assertTrue(p.implies(q));

		q = ManagementLevel.MIX;
		Assert.assertFalse(q.implies(p));
	}

	@Test
	public void testAssignments()
	{
		login();
		final Subject s = SecurityUtils.getSubject();
		Assert.assertTrue(s.isAuthenticated());
		addPermissions(SecurityUtils.getSubject().getId());
		Assert.assertFalse(SecurityUtils.getSubject().isPermitted(
				ManagementLevel.OWN.on(resourceId)));
		Assert.assertTrue(SecurityUtils.getSubject().isPermitted(
				ManagementLevel.MIX.on(resourceId)));
		Assert.assertTrue(SecurityUtils.getSubject().isPermitted(
				ManagementLevel.LOOP.on(resourceId)));

		removePermissions(SecurityUtils.getSubject().getId());
		{
			Assert.assertFalse(SecurityUtils.getSubject().isPermitted(
					ManagementLevel.OWN.on(resourceId)));
			Assert.assertFalse(SecurityUtils.getSubject().isPermitted(
					ManagementLevel.MIX.on(resourceId)));
			Assert.assertFalse(SecurityUtils.getSubject().isPermitted(
					ManagementLevel.LOOP.on(resourceId)));
		}
	}

	@Send
	private AssignManagersCommand addPermissions(String userId)
	{
		return new AssignManagersCommand(resourceId, ManagementLevel.MIX,
				userId);
	}

	@Send
	private AssignManagersCommand removePermissions(String userId)
	{
		return new AssignManagersCommand(resourceId, null, userId);
	}

	private void login()
	{
		SecurityUtils.getSubject().login(username,
				CrmCommandFactory.ACCOUNT_PASSWORD);
	}

	private Project getResource()
	{
		return EntityManagerReference.load(Project.class, resourceId);
	}
}
