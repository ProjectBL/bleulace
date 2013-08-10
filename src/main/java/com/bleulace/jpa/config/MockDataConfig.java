package com.bleulace.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.bleulace.utils.io.CsvIterator;

/**
 * Configures mock data iterators.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configuration
@Profile({ "dev", "test" })
public class MockDataConfig
{
	@DependsOn({ "securityManagerSetter" })
	@Bean(name = "createAccountCommands")
	public Iterable<CreateAccountCommand> mockAccountIterator()
	{
		return new CsvIterator<CreateAccountCommand>(
				CreateAccountCommand.class, new String[] { "email", "password",
						"firstName", "lastName" }, new CellProcessor[] {
						new Optional(), new Optional(), new Optional(),
						new Optional() }, "mockAccounts");
	}
}