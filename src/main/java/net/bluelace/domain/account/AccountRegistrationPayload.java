package net.bluelace.domain.account;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AccountRegistrationPayload
{
	private String firstName;
	private String lastName;
	private String email;
}
