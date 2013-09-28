package com.bleulace.domain.crm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.resource.model.AbstractResource;

@Entity
@RooJavaBean
public class AccountGroup extends AbstractResource
{
	@Column(unique = true, nullable = false)
	private String title;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> members = new HashSet<Account>();

	AccountGroup()
	{
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account member : members)
		{
			members.remove(member);
		}
	}
}