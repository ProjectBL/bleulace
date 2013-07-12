package com.bleulace.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.bleulace.accountRelations.domain.Account;
import com.bleulace.io.utils.CsvIterator;

/**
 * Configures mock data iterators.
 * 
 * @author Arleigh Dickerson
 * 
 */
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