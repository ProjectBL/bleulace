package com.bleulace.domain.project;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class Project extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = -1852176381989700384L;

	@Column(nullable = false)
	private String title;

	@MapKey(name = "title")
	@OneToMany(cascade = CascadeType.ALL)
	private Map<String, Bundle> bundles = new HashMap<String, Bundle>();

	public Project()
	{
	}
}