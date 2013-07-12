package com.bleulace.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.project.Task;
import com.bleulace.security.Encryptor;
import com.frugalu.api.messaging.jpa.EntityManagerReference;

@Entity
@RooJavaBean
public class Account implements Persistable<String>
{
	private static final long serialVersionUID = -8047989744778433448L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	private String id;

	private byte[] hash;
	private byte[] salt;

	@Email
	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@NotNull
	@Column(nullable = false, updatable = false)
	private String firstName;

	@NotNull
	@Column(nullable = false, updatable = false)
	private String lastName;

	@ManyToMany
	private List<Task> tasks = new ArrayList<Task>();

	public String getPassword()
	{
		return "";
	}

	public void setPassword(String password)
	{
		Encryptor encryptor = new Encryptor(password.toCharArray());
		this.hash = encryptor.getHash();
		this.salt = encryptor.getSalt();
	}

	@Transient
	public String getName()
	{
		return firstName + " " + lastName;
	}

	@Override
	public String toString()
	{
		return getName();
	}

	@Transactional(readOnly = true)
	public static Account findByEmail(String email)
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).where(a.email.eq(email)).uniqueResult(a);
	}

	@Transactional(readOnly = true)
	public static Account findById(Long id)
	{
		return EntityManagerReference.get().find(Account.class, id);
	}

	@Override
	public boolean isNew()
	{
		return id == null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Account other = (Account) obj;
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		return true;
	}

	public static Account login(String username, String password)
	{
		SecurityUtils.getSubject().login(
				new UsernamePasswordToken(username, password));
		return findByEmail(username);
	}

	public static Account current()
	{
		try
		{
			Object id = SecurityUtils.getSubject().getPrincipal();
			if (id != null)
			{
				return EntityManagerReference.get().getReference(Account.class,
						id);
			}
		}
		catch (UnavailableSecurityManagerException e)
		{
		}
		return null;
	}

	public static List<Account> findAll()
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).list(a);
	}

	@Transactional(readOnly = true)
	public static Account logout()
	{
		Object id = SecurityUtils.getSubject().getPrincipal();
		SecurityUtils.getSubject().logout();
		return EntityManagerReference.get().getReference(Account.class, id);
	}
}