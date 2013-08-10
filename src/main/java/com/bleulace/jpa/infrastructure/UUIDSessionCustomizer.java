package com.bleulace.jpa.infrastructure;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sequencing.UUIDSequence;
import org.eclipse.persistence.sessions.Session;

public class UUIDSessionCustomizer implements SessionCustomizer
{
	@Override
	public void customize(Session session) throws Exception
	{
		session.getLogin().addSequence(new UUIDSequence("system-uuid"));
	}
}