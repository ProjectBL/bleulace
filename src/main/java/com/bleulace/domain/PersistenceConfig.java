package com.bleulace.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.eclipse.persistence.platform.database.HSQLPlatform;
import org.h2.Driver;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.frugalu.domain")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class PersistenceConfig
{
	@Bean
	public DataSource dataSource()
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:h2:mem:bluelace;DB_CLOSE_ON_EXIT=FALSE");
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

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf)
	{
		JpaTransactionManager txManager = new JpaTransactionManager(emf);
		EclipseLinkJpaDialect dialect = new EclipseLinkJpaDialect();

		// don't call jdbc directly
		dialect.setLazyDatabaseTransaction(true);
		txManager.setJpaDialect(dialect);
		return txManager;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
			DataSource dataSource)
	{
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("persistenceUnit");
		factoryBean.setPackagesToScan("com.bleulace.domain");
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaPropertyMap(jpaPropsMap());
		factoryBean.setPersistenceProvider(new PersistenceProvider());
		return factoryBean;
	}

	protected Map<String, String> jpaPropsMap()
	{
		Map<String, String> props = new HashMap<String, String>();
		props.put("eclipselink.target-database", HSQLPlatform.class.getName());
		props.put("eclipselink.ddl-generation", "drop-and-create-tables");
		props.put("eclipselink.ddl-generation.output-mode", "database");
		props.put("eclipselink.weaving", "static");
		props.put("eclipselink.logging.level.sql", "FINE");
		props.put("eclipselink.logging.parameters", "true");
		props.put("eclipselink.session.customizer",
				UUIDSequence.class.getName());
		return props;
	}
}