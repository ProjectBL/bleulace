package net.bluelace.messaging;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.frugalu.api.messaging.MessageBusFactoryBean;
import com.frugalu.api.messaging.MessageBusRegistrationPostProcessor;

@Configuration
@ComponentScan(basePackageClasses = { MessageBusRegistrationPostProcessor.class })
public class MessagingConfig
{
	@Bean
	public MessageBusFactoryBean messageBus(Executor executor)
	{
		MessageBusFactoryBean config = new MessageBusFactoryBean();
		config.setExecutor(executor);
		return config;
	}
}