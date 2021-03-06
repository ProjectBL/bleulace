package com.bleulace.jpa.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLTemplates;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.EntityPath;

/**
 * Configures JPAQuery instance for use with EclipseLink.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configurable(preConstruction = true)
public class QueryFactory
{
	private static final JPQLTemplates TEMPLATES = EclipseLinkTemplates.DEFAULT;

	@PersistenceContext
	private EntityManager entityManager;

	private QueryFactory()
	{
	}

	public static JPAQuery make()
	{
		return new JPAQuery(new QueryFactory().entityManager, TEMPLATES);
	}

	public static JPAQuery from(EntityPath<?> entity)
	{
		return make().from(entity);
	}

	public static JPADeleteClause delete(EntityPath<?> entity)
	{
		return new JPADeleteClause(entityManager(), entity, TEMPLATES);
	}

	public static JPAUpdateClause update(EntityPath<?> entity)
	{
		return new JPAUpdateClause(entityManager(), entity, TEMPLATES);
	}

	private static EntityManager entityManager()
	{
		return new QueryFactory().entityManager;
	}
}