package net.bluelace.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import net.bluelace.domain.QueryFactory;
import net.bluelace.domain.project.Task;
import net.bluelace.security.Encryptor;

import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.frugalu.api.messaging.jpa.AggregateRoot;
import com.google.common.eventbus.Subscribe;

@Entity
@RooJavaBean
public class Account extends AggregateRoot
{
	private static final long serialVersionUID = -8047989744778433448L;

	private byte[] hash;
	private byte[] salt;

	@Column(nullable = false, unique = true)
	private String email;
	private String firstName;
	private String lastName;

	@ManyToMany
	private List<Task> tasks = new ArrayList<Task>();

	@Subscribe
	public void handleRegistration(AccountRegistrationPayload payload)
	{
		ModelMapper mapper = new ModelMapper();
		mapper.map(payload, this);
	}

	public void setPassword(String password)
	{
		Encryptor encryptor = new Encryptor(password.toCharArray());
		this.hash = encryptor.getHash();
		this.salt = encryptor.getSalt();
	}

	public static Account findByEmail(String email)
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).where(a.email.eq(email)).uniqueResult(a);
	}
}