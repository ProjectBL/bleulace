package net.bluelace.domain.project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bluelace.domain.account.Account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectTest
{
	@PersistenceContext
	private EntityManager entityManager;

	private final Fixtures fixtures = new Fixtures();

	private Project project;

	@Before
	public void beforeTest()
	{
		project = fixtures.getProject();
	}

	@Test
	public void testPersistProject()
	{
		entityManager.persist(project);
	}

	@Test
	public void testAddBundle()
	{
		Bundle bundle = fixtures.getBundle();
		String bundleTitle = "bundle title";
		project.getBundles().put(bundleTitle, bundle);
		entityManager.persist(project);
	}

	@Test
	public void testAddTask()
	{
		Bundle bundle = fixtures.getBundle();
		Task task = fixtures.getTask();

		String taskTitle = "task title";
		bundle.getTasks().put(taskTitle, task);

		String bundleTitle = "bundle title";
		project.getBundles().put(bundleTitle, bundle);

		entityManager.persist(project);
	}

	@Test
	@Transactional
	public void testAddAccountToTask()
	{
		Bundle bundle = fixtures.getBundle();
		Task task = fixtures.getTask();

		String taskTitle = "task title";
		bundle.getTasks().put(taskTitle, task);

		String bundleTitle = "bundle title";
		project.getBundles().put(bundleTitle, bundle);

		entityManager.persist(project);

		Account account = fixtures.getAccount();
		account.getTasks().add(task);
		entityManager.persist(account);
		entityManager.flush();
	}
}