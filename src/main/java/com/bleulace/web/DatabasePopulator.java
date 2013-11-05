package com.bleulace.web;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.crm.Gender;
import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.ContactInformation;
import com.bleulace.domain.management.model.ManagementRole;
import com.bleulace.domain.management.model.Manager;
import com.bleulace.domain.management.model.Project;
import com.bleulace.utils.SystemProfiles;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
@Profile(SystemProfiles.DEV)
public class DatabasePopulator
{
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountDAO dao;

	@Transactional
	public void populate()
	{
		Account me = new Account();
		me.setGender(Gender.MALE);
		me.setId("arleighdickerson@frugalu.com");
		me.setPassword("password");

		ContactInformation myInfo = new ContactInformation("Arleigh",
				"Dickerson", "arleighdickerson@frugalu.com",
				"Marshall University", "Something", "Somewhere");
		me.setContactInformation(myInfo);

		em.persist(me);

		for (int i = 0; i < 5; i++)
		{
			Account a = new Account();
			a.setGender(Gender.MALE);
			a.setId(RandomStringUtils.random(20, true, true) + "@frugalu.com");
			a.setPassword("password");

			ContactInformation aInfo = new ContactInformation(
					RandomStringUtils.random(5, true, false),
					RandomStringUtils.random(5, true, false), a.getUsername(),
					"Marshall University", "Something", "Somewhere");
			a.setContactInformation(aInfo);
			a.getFriends().add(me);
			em.persist(a);
			me.getFriends().add(a);
			a = em.merge(a);
			me = em.merge(me);
		}

		Project project = new Project();
		project.setTitle("World Domination");
		project.getManagers().add(new Manager(me, ManagementRole.OWN));
		em.persist(project);

		Project bundle = new Project();
		bundle.setTitle("Antarctic Domination");
		bundle.getManagers().add(new Manager(me, ManagementRole.OWN));
		project.addChild(bundle);
		em.persist(bundle);

		em.persist(project);
		em.flush();
	}

	// @Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (dao.findOne("arleighdickerson@frugalu.com") == null)
		{
			populate();
		}
	}
}