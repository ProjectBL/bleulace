package com.bleulace.mgt.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.infrastructure.QueryFactory;

class ProjectManagerMap implements Map<Account, ManagementLevel>
{
	private final Project project;

	public ProjectManagerMap(Project project)
	{
		this.project = project;
	}

	@Override
	public int size()
	{
		return project.getMgrList().size();
	}

	@Override
	public boolean isEmpty()
	{
		return project.getMgrList().isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		QProjectManager p = QProjectManager.projectManager;
		if (key instanceof Account)
		{
			Account account = (Account) key;
			return QueryFactory.from(p)
					.where(p.account.eq(account).and(p.project.eq(project)))
					.exists();
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value)
	{
		QProjectManager p = QProjectManager.projectManager;
		if (value instanceof ManagementLevel)
		{
			ManagementLevel level = (ManagementLevel) value;
			return QueryFactory.from(p)
					.where(p.project.eq(project).and(p.level.eq(level)))
					.exists();
		}
		return false;
	}

	@Override
	public ManagementLevel get(Object key)
	{
		if (key instanceof Account)
		{
			ProjectManager mgr = find(key);
			if (mgr != null)
			{
				return mgr.getLevel();
			}
		}
		return null;
	}

	@Override
	public ManagementLevel put(Account key, ManagementLevel value)
	{
		ProjectManager mgr = find(key);
		if (mgr != null && !mgr.getLevel().equals(value))
		{
			project.getMgrList().remove(mgr);
		}
		project.getMgrList().add(new ProjectManager(project, key, value));
		return value;
	}

	@Override
	public ManagementLevel remove(Object key)
	{
		ManagementLevel level = null;
		ProjectManager mgr = find(key);
		if (mgr != null)
		{
			level = mgr.getLevel();
			project.getMgrList().remove(mgr);
			mgr.setProject(null);
		}
		return level;
	}

	@Override
	public void putAll(Map<? extends Account, ? extends ManagementLevel> m)
	{
		for (Entry<? extends Account, ? extends ManagementLevel> entry : m
				.entrySet())
		{
			ProjectManager mgr = find(entry.getKey());
			if (mgr != null)
			{
				project.getMgrList().remove(mgr);
			}
			project.getMgrList().add(
					new ProjectManager(project, entry.getKey(), entry
							.getValue()));
		}
	}

	@Override
	public void clear()
	{
		project.getMgrList().clear();
	}

	@Override
	public Set<Account> keySet()
	{
		QProjectManager p = QProjectManager.projectManager;
		return new HashSet<Account>(QueryFactory.from(p)
				.where(p.project.eq(project)).listResults(p.account)
				.getResults());
	}

	@Override
	public Collection<ManagementLevel> values()
	{
		QProjectManager p = QProjectManager.projectManager;
		return QueryFactory.from(p).where(p.project.eq(project))
				.listResults(p.level).getResults();
	}

	@Override
	public Set<java.util.Map.Entry<Account, ManagementLevel>> entrySet()
	{
		QProjectManager p = QProjectManager.projectManager;
		List<ProjectManager> mgt = QueryFactory.from(p)
				.where(p.project.eq(project)).listResults(p).getResults();
		Set<Entry<Account, ManagementLevel>> entries = new HashSet<Map.Entry<Account, ManagementLevel>>();
		for (final ProjectManager mgr : mgt)
		{
			entries.add(new Entry<Account, ManagementLevel>()
			{
				@Override
				public ManagementLevel setValue(ManagementLevel value)
				{
					mgr.setLevel(value);
					return value;
				}

				@Override
				public ManagementLevel getValue()
				{
					return mgr.getLevel();
				}

				@Override
				public Account getKey()
				{
					return mgr.getAccount();
				}
			});
		}
		return entries;
	}

	private ProjectManager find(Object toFind)
	{

		if (toFind instanceof Account)
		{
			QProjectManager p = QProjectManager.projectManager;
			Account account = (Account) toFind;
			return QueryFactory.from(p)
					.where(p.account.eq(account).and(p.project.eq(project)))
					.singleResult(p);
		}
		return null;
	}
}
