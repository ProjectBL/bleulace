package com.bleulace.domain.project;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class Task
{
	@Id
	private String id = UUID.randomUUID().toString();

	@ManyToOne(optional = false)
	@JoinColumn(updatable = false)
	private Bundle bundle;

	@Column(nullable = false)
	private String title;
}