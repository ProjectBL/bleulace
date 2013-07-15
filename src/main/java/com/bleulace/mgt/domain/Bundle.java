package com.bleulace.mgt.domain;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean(settersByDefault = false)
public class Bundle extends Project implements EventSourcedEntityMixin
{
	private static final long serialVersionUID = -5604228662275728239L;

	@SuppressWarnings("unused")
	private Bundle()
	{
	}

	protected Bundle(Project project, String name)
	{
		registerAggregateRoot(project);
	}
}