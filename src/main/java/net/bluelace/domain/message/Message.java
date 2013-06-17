package net.bluelace.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import net.bluelace.domain.account.Account;

import org.joda.time.LocalDateTime;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.frugalu.api.messaging.jpa.AggregateRoot;

@Entity
@RooJavaBean
public class Message extends AggregateRoot
{
	private static final long serialVersionUID = 704926750683987643L;

	@Column(nullable = false, updatable = false)
	private String title;

	@Column(nullable = false, updatable = false)
	private String body;

	private LocalDateTime sendTime;

	@ManyToOne
	private Account from;

	@ManyToMany
	private List<Account> to = new ArrayList<Account>();

	@PrePersist
	protected void prePersist()
	{
		sendTime = LocalDateTime.now();
	}
}