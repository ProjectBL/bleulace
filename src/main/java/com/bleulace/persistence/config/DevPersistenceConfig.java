package com.bleulace.persistence.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Persistence configuration for Development and Test profiles
 * 
 * @author Arleigh Dickerson
 * 
 */
@Profile({ "dev", "test" })
@Configuration
public class DevPersistenceConfig
{
	@Bean
	public DataSource dataSource()
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(Driver.class.getName());

		// deploy database in memory
		dataSource.setUrl("jdbc:h2:mem:bleulaceDb;DB_CLOSE_ON_EXIT=FALSE");

		// or write database to hard drive
		// dataSource.setUrl("jdbc:h2:~/bleulaceDb;DB_CLOSE_ON_EXIT=FALSE");

		dataSource.setUsername("sa");
		dataSource.setPassword("");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000);
		dataSource.setMinEvictableIdleTimeMillis(1800000);
		dataSource.setNumTestsPerEvictionRun(3);
		return dataSource;
	}

	@Bean(name = "jpaPropsMap")
	public Map<String, String> jpaPropsMap()
	{
		Map<String, String> props = new HashMap<String, String>();
		props.put("eclipselink.target-database", HSQLPlatform.class.getName());
		props.put("eclipselink.logging.level.sql", "FINE");
		props.put("eclipselink.logging.parameters", "true");
		props.put("eclipselink.ddl-generation", "create-or-extend-tables");
		return props;
	}
}