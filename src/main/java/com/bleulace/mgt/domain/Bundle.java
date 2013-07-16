package com.bleulace.mgt.domain;

import javax.persistence.Entity;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean
public class Bundle extends Project implements EventSourcedEntityMixin
{
	private static final long serialVersionUID = -5604228662275728239L;

	Bundle()
	{
	}

	Bundle(Project goal)
	{
		registerAggregateRoot(goal);
	}
}