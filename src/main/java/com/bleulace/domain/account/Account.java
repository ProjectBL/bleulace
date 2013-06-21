package com.bleulace.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.Email;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.project.Task;
import com.bleulace.security.Encryptor;
import com.frugalu.api.messaging.jpa.AggregateRoot;
import com.frugalu.api.messaging.jpa.EntityManagerReference;
import com.google.common.eventbus.Subscribe;

@Entity
@RooJavaBean
public class Account extends AggregateRoot
{
	private static final long serialVersionUID = -8047989744778433448L;

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

	@Subscribe
	public void subscribe(AccountRegistrationPayload command)
	{
		ModelMapper mapper = new ModelMapper();
		mapper.map(command, this);
	}

	public static Account login(String username, String password)
	{
		SecurityUtils.getSubject().login(
				new UsernamePasswordToken(username, password));
		return findByEmail(username);
	}

	public static Account current()
	{
		Object id = SecurityUtils.getSubject().getPrincipal();
		return id == null ? null : EntityManagerReference.get().getReference(
				Account.class, id);
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