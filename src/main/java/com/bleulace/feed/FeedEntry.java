package com.bleulace.feed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;
import com.bleulace.persistence.infrastructure.QueryFactory;

@Entity
public class FeedEntry extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = 1150763613614458205L;

	@Column(nullable = false, updatable = false)
	private String accountId;

	@Column(nullable = false, updatable = false)
	private FeedSubject subject;

	@Lob
	@ElementCollection
	private Map<String, Serializable> payloads;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	FeedEntry(Account account, Map<String, Serializable> payloads)
	{
		Assert.noNullElements(new Object[] { account, payloads });
		Assert.notEmpty(payloads);
		this.subject = new FeedSubject(account);

		this.accountId = account.getId();
		this.payloads = payloads;
	}

	@SuppressWarnings("unused")
	private FeedEntry()
	{
	}

	public static List<FeedEntry> findByAccountId(String accountId)
	{
		QFeedEntry f = QFeedEntry.feedEntry;
		return QueryFactory.from(f).where(f.accountId.eq(accountId))
				.orderBy(f.id.desc()).list(f);
	}

	public String getAccountId()
	{
		return accountId;
	}

	public FeedSubject getSubject()
	{
		return subject;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	@SuppressWarnings("unchecked")
	public <T> T getPayload(Class<T> clazz)
	{
		Serializable value = payloads.get(clazz.getSimpleName());
		return value == null ? null : (T) value;
	}
}