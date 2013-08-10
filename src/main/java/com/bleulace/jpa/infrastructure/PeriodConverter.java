package com.bleulace.jpa.infrastructure;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.joda.time.Period;

/**
 * Converter to store Period values persistently in EL
 * 
 * @author Arleigh Dickerson
 * 
 */
public class PeriodConverter implements Converter
{
	private static final long serialVersionUID = 2506658239859630633L;

	@Override
	public Object convertDataValueToObjectValue(Object dataValue,
			Session session)
	{
		return dataValue == null ? null : Period.minutes((Integer) dataValue);
	}

	@Override
	public Object convertObjectValueToDataValue(Object objectValue,
			Session session)
	{
		return objectValue == null ? null : ((Period) objectValue).getMinutes();
	}

	@Override
	public boolean isMutable()
	{
		return false;
	}

	@Override
	public void initialize(DatabaseMapping mapping, Session session)
	{
		// TODO Auto-generated method stub

	}

}
