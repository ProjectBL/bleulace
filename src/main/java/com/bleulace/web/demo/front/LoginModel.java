package com.bleulace.web.demo.front;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
class LoginModel
{
	@NotEmpty
	private String username = "";

	@NotEmpty
	private String password = "";

	@NotNull
	private boolean rememberMe = false;
}