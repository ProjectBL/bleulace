package com.bleulace.mgt.domain;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;

import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.JodaTimeConverters.DateToLocalDateTimeConverter;

public class FutureRangeValidator implements
		ConstraintValidator<Future, DateRange>
{
	private Converter<Date, LocalDateTime> converter = DateToLocalDateTimeConverter.INSTANCE;

	@Override
	public void initialize(Future constraintAnnotation)
	{
		return;
	}

	@Override
	public boolean isValid(DateRange value, ConstraintValidatorContext context)
	{
		Date startDate = value.getStart();
		Date endDate = value.getEnd();

		if (startDate == null || endDate == null)
		{
			return false;
		}

		LocalDateTime start = converter.convert(startDate);
		LocalDateTime end = converter.convert(endDate);

		if (end.isBefore(start) || end.equals(start))
		{
			return false;
		}

		if (start.isBefore(LocalDateTime.now()))
		{
			return false;
		}

		return true;
	}
}