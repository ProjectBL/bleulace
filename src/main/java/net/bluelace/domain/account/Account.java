package net.bluelace.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import net.bluelace.domain.QueryFactory;
import net.bluelace.domain.project.Task;
import net.bluelace.persistent.QAccount;
import net.bluelace.security.Encryptor;

import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean
public class Account implements Persistable<String>
{
	private static final long serialVersionUID = -8047989744778433448L;

	@Id
	@Column(updatable = false)
	private String id;

	private byte[] hash;
	private byte[] salt;

	@Override
	public boolean isNew()
	{
		return id == null;
	}

	public void setPassword(String password)
	{
		Encryptor encryptor = new Encryptor(password.toCharArray());
		this.hash = encryptor.getHash();
		this.salt = encryptor.getSalt();
	}

	public static Account findById(String id)
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).where(a.id.eq(id)).uniqueResult(a);
	}

	@ManyToMany
	private List<Task> tasks = new ArrayList<Task>();
}
