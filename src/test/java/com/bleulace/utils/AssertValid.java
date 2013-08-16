package com.bleulace.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class AssertValid
{
	private static final AssertValid INSTANCE = new AssertValid();

	@Autowired
	private Validator validator;

	private AssertValid()
	{
	}

	public static void is(Object bean)
	{
		INSTANCE.doAssertion(bean, true);
	}

	public static void isNot(Object bean)
	{
		INSTANCE.doAssertion(bean, false);
	}

	private void doAssertion(Object bean, boolean value)
	{
		for (ConstraintViolation<?> v : validator.validate(bean))
		{
			if (value)
			{
				Assert.fail(v.getMessage());
			}
			else
			{
				return;
			}
		}
		if (!value)
		{
			Assert.fail("Bean " + bean
					+ " passed validation and was not expected to");
		}
	}
}
