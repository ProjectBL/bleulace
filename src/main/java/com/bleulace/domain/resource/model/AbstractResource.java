package com.bleulace.domain.resource.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.axonframework.domain.IdentifierFactory;
import org.springframework.roo.addon.equals.RooEquals;

@RooEquals
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "RESOURCE")
public abstract class AbstractResource implements Resource, Serializable
{
	@Id
	@Column(nullable = false, updatable = false)
	private String id = IdentifierFactory.getInstance().generateIdentifier();

	AbstractResource()
	{
	}

	@Override
	public String getId()
	{
		return id;
	}

	protected void setId(String id)
	{
		this.id = id;
	}

}