package com.bleulace.jpa.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.Send;
import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.model.ContactInformation;
import com.bleulace.utils.SystemProfiles;

@Component
@Profile(SystemProfiles.DEV)
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	private static final String PASSWORD = "password";

	public void populate()
	{
		makeAccount("arleighdickerson@frugalu.com", "Arleigh", "Dickerson");
	}

	@Send(async = true)
	private CreateAccountCommand makeAccount(String username, String firstName,
			String lastName)
	{
		ContactInformation info = new ContactInformation();
		info.setFirstName(firstName);
		info.setLastName(lastName);
		info.setEmail(username);
		return new CreateAccountCommand(username, PASSWORD, info);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		try
		{
			populate();
		}
		catch (Throwable t)
		{
			return;
		}
	}
}