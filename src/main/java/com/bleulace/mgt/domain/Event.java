package com.bleulace.mgt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Converters;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.domain.event.EventCreatedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.persistence.infrastructure.LocalDateTimeConverter;
import com.bleulace.persistence.infrastructure.PeriodConverter;

@RooJavaBean
@Entity
@Converters(value = {
		@Converter(name = "localDateTimeConverter", converterClass = LocalDateTimeConverter.class),
		@Converter(name = "periodConverter", converterClass = PeriodConverter.class) })
public class Event extends Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 8727887519388582258L;

	@Column(nullable = false)
	@Convert("localDateTimeConverter")
	private LocalDateTime start;

	@Column(nullable = false)
	@Convert("periodConverter")
	private Period length;

	@ManyToMany
	private List<Account> invitees = new ArrayList<Account>();

	Event()
	{
	}

	public Event(CreateEventCommand command)
	{
		super(command.getId());
		apply(command, EventCreatedEvent.class);
	}

	public void on(EventCreatedEvent event)
	{
		super.on(event);
		start = LocalDateTime.fromDateFields(event.getStart());
		LocalDateTime end = LocalDateTime.fromDateFields(event.getEnd());
		length = Period.fieldDifference(start, end);
		// TODO : feed entry
	}
}