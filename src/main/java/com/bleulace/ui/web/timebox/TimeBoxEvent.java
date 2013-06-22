package com.bleulace.ui.web.timebox;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;

@RooJavaBean
public class TimeBoxEvent
{
	@NotEmpty
	private String what;

	@NotEmpty
	private String where;

	@NotNull
	@Future
	private Date when;

	@NotNull
	private Account who;
}
