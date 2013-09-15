package com.bleulace.jpa.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.platform.database.MySQLPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bleulace.utils.SystemProfiles;
import com.mysql.jdbc.Driver;

/**
 * Persistence configuration for production profile. This class will acquire
 * Amazon RDS connection params from system properties.
 * 
 * Clearly, this will have to be altered if we do not use RDS as our database
 * server.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configuration
@Profile(SystemProfiles.PROD)
public class ProdPersistenceConfig
{
	private String dbName = System.getProperty("RDS_DB_NAME");
	private String username = System.getProperty("RDS_USERNAME");
	private String password = System.getProperty("RDS_PASSWORD");
	private String hostname = System.getProperty("RDS_HOSTNAME");
	private String port = System.getProperty("RDS_PORT");

	@Bean
	public DataSource dataSource()
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://" + hostname + ":" + port + "/"
				+ dbName);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "jpaPropsMap")
	public Map<String, String> jpaPropsMap()
	{
		Map<String, String> props = new HashMap<String, String>();
		props.put("eclipselink.target-database", MySQLPlatform.class.getName());
		props.put("eclipselink.ddl-generation", "drop-and-create-tables");
		return props;
	}
}