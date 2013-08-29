package com.bleulace.jpa.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bleulace.utils.SystemProfiles;

/**
 * Persistence Configuration shared across all system profiles.
 * 
 * @author Arleigh Dickerson
 * @see SystemProfiles
 * 
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
class BasePersistenceConfig
{
	@Resource(name = "jpaPropsMap")
	private Map<String, String> jpaPropsMap;

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
		Map<String, String> allProps = new HashMap<String, String>();
		allProps.putAll(baseJpaProps());
		allProps.putAll(jpaPropsMap);

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("persistenceUnit");
		factoryBean.setPackagesToScan(new String[] { "com.bleulace",
				"org.axonframework" });
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaPropertyMap(allProps);
		factoryBean.setPersistenceProvider(new PersistenceProvider());
		return factoryBean;
	}

	private Map<String, String> baseJpaProps()
	{
		Map<String, String> props = new HashMap<String, String>();
		props.put("eclipselink.weaving", "static");
		props.put("eclipselink.ddl-generation.output-mode", "database");
		props.put("eclipselink.session.customizer",
				UUIDSessionCustomizer.class.getName());
		return props;
	}
}