package com.bleulace.messaging;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.data.jpa.domain.AbstractAuditable;

import com.bleulace.domain.account.Account;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersistentEvent extends AbstractAuditable<Account, Long>
{
	private static final long serialVersionUID = 8758753138076586184L;
}
