package com.bleulace.domain.metrics;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.bleulace.domain.account.Account;

@Component
public class AuditorAwareImpl implements AuditorAware<Account>
{
	@Override
	public Account getCurrentAuditor()
	{
		return Account.current();
	}
}