package com.bleulace.crm.infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.axonframework.domain.DomainEventStream;
import org.axonframework.eventstore.EventStore;

import com.bleulace.crm.domain.Account;
import com.bleulace.crm.domain.AccountGroup;
import com.bleulace.crm.domain.GroupDAO;
import com.bleulace.mgt.domain.Project;
import com.bleulace.mgt.domain.ProjectDAO;
import com.bleulace.mgt.domain.Task;
import com.bleulace.mgt.domain.TaskDAO;
import com.bleulace.utils.CompositeDomainEventStream;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.jpa.EntityManagerReference;

public class FeedEventStreamBuilder
{
	private final String accountId;

	public FeedEventStreamBuilder(String accountId)
	{
		this.accountId = accountId;
	}

	public DomainEventStream build()
	{
		Set<DomainEventStream> streams = new HashSet<DomainEventStream>();
		EventStore eventStore = SpringApplicationContext
				.getBean(EventStore.class);
		for (Entry<String, String> entry : getInterestMap().entrySet())
		{
			streams.add(eventStore.readEvents(entry.getKey(), entry.getValue()));
		}
		return new CompositeDomainEventStream(
				streams.toArray(new DomainEventStream[streams.size()]));
	}

	private Map<String, String> getInterestMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		Account account = EntityManagerReference.load(Account.class, accountId);
		Set<Account> accounts = new HashSet<Account>(account.getFriends());
		accounts.add(account);

		for (Account a : accounts)
		{
			map.put(account.getClass().getSimpleName(), a.getId());
		}

		List<AccountGroup> groups = SpringApplicationContext.getBean(
				GroupDAO.class).findByMemberId(account.getId());
		for (AccountGroup g : groups)
		{
			map.put(g.getClass().getSimpleName(), g.getId());
		}

		List<Project> projects = SpringApplicationContext.getBean(
				ProjectDAO.class).findByAssignment(account.getId());
		for (Project project : projects)
		{
			map.put(project.getClass().getSimpleName(), project.getId());
		}

		List<Task> tasks = SpringApplicationContext.getBean(TaskDAO.class)
				.findByAccountId(account.getId());
		for (Task task : tasks)
		{
			map.put(task.getClass().getSimpleName(), task.getId());
		}

		return map;
	}
}