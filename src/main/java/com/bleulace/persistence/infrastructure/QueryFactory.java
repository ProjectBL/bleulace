package com.bleulace.persistence.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLTemplates;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
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

	public static JPAQuery from(EntityPath<?> entity)
	{
		return new JPAQuery(new QueryFactory().entityManager, TEMPLATES)
				.from(entity);
	}

	public static JPADeleteClause delete(EntityPath<?> entity)
	{
		return new JPADeleteClause(entityManager(), entity);
	}

	private static EntityManager entityManager()
	{
		return new QueryFactory().entityManager;
	}
}