package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Bundle extends AbstractAnnotatedEntity implements Serializable
{
	private static final long serialVersionUID = -5604228662275728239L;

	@Id
	@Column(updatable = false, nullable = false)
	private String id = IdentifierFactory.getInstance().generateIdentifier();

	@Column(nullable = false)
	private String name;

	@SuppressWarnings("unused")
	private Bundle()
	{
	}

	protected Bundle(Project project, String name)
	{
		registerAggregateRoot(project);
	}
}