package com.bleulace.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.bleulace.domain.account.Account;
import com.bleulace.utils.CsvIterator;

@Profile({ "dev", "prod" })
@Configuration
public class MockDataConfig
{
	@Bean(name = "mockAccountIterator")
	public Iterable<Account> mockAccountIterator()
	{
		return new CsvIterator<Account>(Account.class, new String[] { "email",
				"password", "firstName", "lastName" },
				new CellProcessor[] { new Optional(), new Optional(),
						new Optional(), new Optional() }, "mockAccounts");
	}
}