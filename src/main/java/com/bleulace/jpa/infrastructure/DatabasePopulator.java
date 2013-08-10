package com.bleulace.jpa.infrastructure;

import java.util.Iterator;

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
import com.bleulace.mgt.application.command.InviteGuestsCommand;
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
		Iterator<CreateAccountCommand> it = createAccountCommands.iterator();

		it.next();

		CreateAccountCommand createArlsCommand = it.next();
		gateway().sendAndWait(createArlsCommand);

		CreateAccountCommand createSomeoneElse = it.next();
		gateway().sendAndWait(createSomeoneElse);

		doLogin(createArlsCommand);
		makeEvent();
		doLogout();

		doLogin(createSomeoneElse);
		InviteGuestsCommand igc = new InviteGuestsCommand(makeEvent().getId());
		igc.getGuestIds().add(createArlsCommand.getId());
		gateway().sendAndWait(igc);
		doLogout();
	}

	private Long countAccounts()
	{
		return new SimpleJpaRepository<Account, String>(Account.class,
				EntityManagerReference.get()).count();
	}

	private CreateEventCommand makeEvent()
	{
		CreateEventCommand cec = new CreateEventCommand();
		cec.setTitle("My First Event");
		cec.setLocation("An undisclosed location");
		gateway().sendAndWait(cec);
		return cec;
	}

	private void doLogout()
	{
		SecurityUtils.getSubject().logout();
	}

	private void doLogin(CreateAccountCommand command)
	{
		SecurityUtils.getSubject().login(
				new UsernamePasswordToken(command.getEmail(), command
						.getPassword()));
	}
}