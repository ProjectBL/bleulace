package com.bleulace.domain.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.shiro.authz.permission.DomainPermission;
import org.springframework.data.domain.Persistable;

import com.bleulace.domain.calendar.CalendarEntry;

@Entity
public class CalendarEntryPermission extends DomainPermission implements
		Persistable<String>
{
	@Id
	@GeneratedValue(generator = "system-uuid")
	private String id;

	private static final long serialVersionUID = 999194762091223011L;

	private CalendarEntryPermission()
	{
	}

	public CalendarEntryPermission(Account account, CalendarEntry entry)
	{
		super("edit", entry.getId().toString());
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public boolean isNew()
	{
		return id == null;
	}
}
