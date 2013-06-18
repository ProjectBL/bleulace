package net.bluelace.domain.calendar;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import net.bluelace.domain.account.Account;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean
public class CalendarEntryInvitee implements Serializable
{
	private static final long serialVersionUID = -5325769114002875339L;

	@ManyToOne
	private Account account;

	@Enumerated
	private RSVPStatus status;
}
