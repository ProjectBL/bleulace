package com.bleulace.domain.resource.infrastructure;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.management.model.Project;
import com.bleulace.domain.management.model.Task;
import com.bleulace.jpa.EntityManagerReference;

public class ResourceDAOTest implements IntegrationTest
{
	@Autowired
	private ResourceDAO dao;

	@Autowired
	private ManagementCommandFactory factory;

	private Map<Class<?>, String> ids;

	@Before
	public void initializeResources()
	{
		ids = factory.initializeDefaultGroup();
	}

	@Test
	public void testFindChildren()
	{
		List<Bundle> bundles = dao.findChildren(ids.get(Project.class),
				Bundle.class);
		Assert.assertTrue(bundles.size() == 1);

		List<Task> tasks = dao.findChildren(ids.get(Project.class), Task.class);
		Assert.assertTrue(tasks.size() == 0);
	}

	<T> T find(Class<T> clazz)
	{
		return EntityManagerReference.load(clazz, ids.get(clazz));
	}
}