package com.bleulace.domain.calendar;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;

@Entity
@RooJavaBean
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_ID",
		"ENTRY_ID" }) })
public class CalendarEntryParticipant extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = -5325769114002875339L;

	@ManyToOne
	@JoinColumn(name = "ENTRY_ID")
	private CalendarEntry entry;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;

	@Enumerated
	private ParticipationStatus status;
}
