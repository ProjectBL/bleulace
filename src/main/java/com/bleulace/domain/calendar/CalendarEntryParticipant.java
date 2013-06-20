package com.bleulace.domain.calendar;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;

@Embeddable
@RooJavaBean
public class CalendarEntryParticipant implements Serializable
{
	private static final long serialVersionUID = -5325769114002875339L;

	@ManyToOne
	private Account account;

	@Enumerated
	private ParticipationStatus status;
}
