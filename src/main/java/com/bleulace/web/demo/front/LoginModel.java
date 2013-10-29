package com.bleulace.web.demo.front;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.utils.SystemProfiles;

@RooJavaBean
@Configurable
public class LoginModel implements Serializable
{
	@Autowired
	private transient Environment env;

	@NotEmpty
	private String username = "";

	@NotEmpty
	private String password = "";

	@NotNull
	private boolean rememberMe = false;

	@PostConstruct
	protected void init()
	{
		if (env.acceptsProfiles(SystemProfiles.DEV))
		{
			username = "arleighdickerson@frugalu.com";
			password = "password";
		}
	}
}