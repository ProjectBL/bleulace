package com.bleulace.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.utils.SystemProfiles;
import com.bleulace.utils.io.CsvIterator;

@Configuration
@Profile(SystemProfiles.DEV)
public class MockDataConfig
{
	@DependsOn({ "securityManagerSetter" })
	@Bean(name = "createAccountCommands")
	public Iterable<CreateAccountCommand> createAccountCommands()
	{
		return new CsvIterator<CreateAccountCommand>(
				CreateAccountCommand.class, new String[] { "username",
						"password", "firstName", "lastName" },
				new CellProcessor[] { new Optional(), new Optional(),
						new Optional(), new Optional() }, "mockAccounts");
	}
}