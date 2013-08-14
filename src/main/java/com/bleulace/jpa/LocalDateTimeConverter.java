package com.bleulace.jpa;

import java.sql.Timestamp;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.joda.time.LocalDateTime;

/**
 * Converter to store LocalDateTime values persistently in EL
 * 
 * @author Arleigh Dickerson
 * 
 */
public class LocalDateTimeConverter implements Converter
{
	private static final long serialVersionUID = 362089437264505921L;

	@Override
	public Object convertDataValueToObjectValue(Object dataValue,
			Session session)
	{
		if (dataValue == null)
		{
			return null;
		}
		Timestamp ts = (Timestamp) dataValue;
		return new LocalDateTime(ts);
	}

	@Override
	public Object convertObjectValueToDataValue(Object objectValue,
			Session session)
	{
		return objectValue == null ? null : new Timestamp(
				((LocalDateTime) objectValue).toDateTime().getMillis());
	}

	@Override
	public boolean isMutable()
	{
		return false;
	}

	@Override
	public void initialize(DatabaseMapping mapping, Session session)
	{
		return;
	}
}