package com.bleulace.cqrs.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.bleulace.cqrs.CommandPayload;
import com.bleulace.cqrs.DomainEventPayload;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultHandler
{
	Class<? extends CommandPayload> command();

	Class<? extends DomainEventPayload> event();
}