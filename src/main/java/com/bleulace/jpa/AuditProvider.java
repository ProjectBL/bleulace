package com.bleulace.jpa;

import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;

@Component
class AuditProvider implements AuditorAware<Account>, DateTimeProvider
{
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public Account getCurrentAuditor()
	{
		try
		{
			String id = (String) SecurityUtils.getSubject().getPrincipal();
			return id == null ? null : accountDAO.findOne(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DateTime getDateTime()
	{
		// naive
		return DateTime.now();
	}
}