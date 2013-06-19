package net.bluelace.domain.project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bluelace.domain.QueryFactory;
import net.bluelace.domain.account.Account;
import net.bluelace.domain.account.AccountRegistrationPayload;
import net.bluelace.domain.account.QAccount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.frugalu.api.messaging.command.Command;

@Transactional
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
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
	}

	@Test
	@Transactional
	public void testCommand() throws Exception
	{
		AccountRegistrationPayload p = new AccountRegistrationPayload();
		p.setEmail("foo@bar.com");
		p.setFirstName("A");
		p.setLastName("D");
		new Command(p).withoutCallback().onAggregate(Account.class, null)
				.send();
		p.setEmail("foo@bzr.com");
		new Command(p).withoutCallback().onAggregate(Account.class, null)
				.send();
		Thread.sleep(100);
		QAccount a = QAccount.account;
		System.out.println(QueryFactory.from(a).listResults(a).getResults()
				.size());
	}
}