package net.bluelace.persistent;

import javax.persistence.Entity;
import javax.persistence.Id;

import net.bluelace.security.Encryptor;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Persistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

@Entity
@RooJavaBean(settersByDefault = false)
public class Account implements Persistable<String>
{
	private static final long serialVersionUID = -8047989744778433448L;

	@Id
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
		try
		{
			BeanUtils.copyProperties(this,
					new Encryptor(password.toCharArray()));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Account findById(String id)
	{
		QAccount a = QAccount.account;
		return QueryFactory.from(a).where(a.id.eq(id)).uniqueResult(a);
	}
}
