package com.bleulace.domain.management.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class PersistentEvent extends Project
{
	@NotEmpty
	@Column(nullable = false)
	private String location = "";

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date start;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date end;

	@CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = {
			"PeristentEvent_ID", "ACCOUNT_ID" }))
	@ElementCollection(targetClass = EventParticipant.class, fetch = FetchType.LAZY)
	private List<EventParticipant> invitees = new ArrayList<EventParticipant>();

	public PersistentEvent()
	{
	}
}