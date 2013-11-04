package com.bleulace.domain.management.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	@ElementCollection
	private List<EventInvitee> invitees = new ArrayList<EventInvitee>();

	public PersistentEvent()
	{
	}
}