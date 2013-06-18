package net.bluelace.ui;

import net.bluelace.domain.account.Account;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		Account account = new Account();
		account.setFirstName("Arleigh");
		account.setLastName("Dickerson");
		account.setEmail("arleighdickerson@frugalu.com");
		account.setPassword("password");
		account.save();
	}
}