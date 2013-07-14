package com.bleulace.mgt.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Bundle extends Project
{
	private static final long serialVersionUID = -5604228662275728239L;

	@JoinColumn
	@ManyToOne
	private Project project;

	Bundle()
	{
	}
}