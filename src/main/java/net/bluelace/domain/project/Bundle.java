package net.bluelace.domain.project;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class Bundle
{
	@Id
	private String id = UUID.randomUUID().toString();

	@OneToMany(cascade = CascadeType.ALL)
	@MapKey(name = "title")
	private Map<String, Task> tasks = new HashMap<String, Task>();

	@Column(nullable = false)
	private String title;

	Bundle()
	{
	}
}