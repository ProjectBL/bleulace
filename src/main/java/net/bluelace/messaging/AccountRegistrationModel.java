package net.bluelace.messaging;

import java.io.Serializable;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AccountRegistrationModel implements Serializable
{
	private static final long serialVersionUID = 7688782451794572331L;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@Email
	private String email;

	// @PhoneNumber
	private String phone;

	// @Valid
	// private StreetAddress streetAddress;

	@Past
	private LocalDate birthday;
}