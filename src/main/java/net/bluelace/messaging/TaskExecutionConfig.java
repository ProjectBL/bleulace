package net.bluelace.messaging;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync(mode = AdviceMode.ASPECTJ)
@EnableScheduling
public class TaskExecutionConfig implements AsyncConfigurer
{
	private static final Integer MAX_THREAD_POOL_SIZE = 5;

	@Bean
	@Override
	public Executor getAsyncExecutor()
	{
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(MAX_THREAD_POOL_SIZE);
		executor.initialize();
		return executor;
	}
}