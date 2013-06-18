package net.bluelace.domain.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import net.bluelace.domain.account.Account;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class CalendarEntry extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = 1831178477077720532L;

	@ManyToOne
	private Account owner;

	@ElementCollection
	private List<Account> invitees = new ArrayList<Account>();

	private String title;

	private LocalDateTime date;

	public CalendarEntry()
	{
	}
}