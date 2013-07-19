package com.bleulace.mgt.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Converters;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;
import com.bleulace.persistence.infrastructure.LocalDateTimeConverter;
import com.bleulace.persistence.infrastructure.PeriodConverter;

@RooJavaBean
@Entity
@Converters(value = {
		@Converter(name = "localDateTimeConverter", converterClass = LocalDateTimeConverter.class),
		@Converter(name = "periodConverter", converterClass = PeriodConverter.class) })
public class MgtEvent extends Project implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = 8727887519388582258L;

	@Convert("localDateTimeConverter")
	private LocalDateTime start;

	@Convert("periodConverter")
	private Period length;

	@Enumerated(EnumType.STRING)
	@MapKeyJoinColumn
	@ManyToMany(cascade = CascadeType.ALL)
	private Map<Account, Role> attendees = new HashMap<Account, MgtEvent.Role>();

	public enum Role
	{
		INVITEE, ORGANIZER
	}
}