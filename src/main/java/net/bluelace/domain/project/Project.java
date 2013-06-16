package net.bluelace.domain.project;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Project
{
	@Id
	private String id = UUID.randomUUID().toString();

	@MapKey(name = "title")
	@OneToMany(cascade = CascadeType.ALL)
	private Map<String, Bundle> bundles = new HashMap<String, Bundle>();

	public Project()
	{
	}
}