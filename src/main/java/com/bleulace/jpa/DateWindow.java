package com.bleulace.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Range;
import org.springframework.data.convert.JodaTimeConverters.DateToLocalDateTimeConverter;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@Embeddable
@RooEquals
@RooJavaBean(settersByDefault = false)
public class DateWindow implements Serializable
{
	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date start;

	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date end;

	DateWindow()
	{
	}

	public DateWindow(Date from, Date to)
	{
		Assert.notNull(from);
		Assert.notNull(to);
		Range<Date> range = Range.between(from, to);
		start = range.getMinimum();
		end = range.getMaximum();
	}

	public Date getStart()
	{
		return start;
	}

	public Date getEnd()
	{
		return end;
	}

	public DateWindow withEnd(Date end)
	{
		return new DateWindow(getStart(), end);
	}

	public DateWindow withStart(Date start)
	{
		return new DateWindow(start, getEnd());
	}

	public DateWindow move(Date newStart)
	{
		return new JodaDateWindow(getStart(), getEnd()).move(
				DateToLocalDateTimeConverter.INSTANCE.convert(newStart))
				.getDateWindow();
	}

	public boolean isAllDay()
	{
		return new JodaDateWindow(getStart(), getEnd()).getLength().getDays() > 0;
	}

	public JodaDateWindow getDecorator()
	{
		return new JodaDateWindow(getStart(), getEnd());
	}
}