package com.bleulace.web.demo.front;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class LoginModel implements Serializable
{
	@NotEmpty
	private String username = "";

	@NotEmpty
	private String password = "";

	@NotNull
	private boolean rememberMe = false;
}