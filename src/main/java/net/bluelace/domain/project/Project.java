package net.bluelace.domain.project;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.frugalu.api.messaging.jpa.AggregateRoot;

@Entity
@RooJavaBean
public class Project extends AggregateRoot
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