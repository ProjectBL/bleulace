package com.bleulace.persistence.infrastructure;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.utils.jpa.EntityManagerReference;

/**
 * Fill the database with dummy values
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
@Profile({ "dev", "prod" })
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>, CommandGatewayAware
{
	@Autowired
	@Qualifier("createAccountCommands")
	private Iterable<CreateAccountCommand> createAccountCommands;

	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (shouldPopulate())
		{
			populate();

		}
	}

	protected boolean shouldPopulate()
	{
		return countAccounts() < 1;
	}

	protected void populate()
	{
		for (CreateAccountCommand command : createAccountCommands)
		{
			gateway().sendAndWait(command);
			SecurityUtils.getSubject().login(
					new UsernamePasswordToken(command.getEmail(), command
							.getPassword()));

			CreateProjectCommand cpc = new CreateProjectCommand();
			cpc.setTitle("My First Project");
			gateway().send(cpc);

			CreateEventCommand cec = new CreateEventCommand();
			cec.setTitle("My First Event");
			cec.setLocation("An undisclosed location");
			gateway().send(cec);
		}
	}

	private Long countAccounts()
	{
		return new SimpleJpaRepository<Account, String>(Account.class,
				EntityManagerReference.get()).count();
	}
}