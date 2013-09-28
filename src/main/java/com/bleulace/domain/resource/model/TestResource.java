package com.bleulace.domain.resource.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class TestResource extends AbstractPersistable<Long>
{
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = true)
	private TestResource parent;

	@OneToMany(cascade = CascadeType.ALL)
	private List<TestResource> children = new ArrayList<TestResource>();

	public void addChild(TestResource child)
	{
		child.setParent(this);
		children.add(child);
	}
}