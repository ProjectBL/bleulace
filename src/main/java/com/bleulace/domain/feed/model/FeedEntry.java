package com.bleulace.domain.feed.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@Embeddable
@RooJavaBean(settersByDefault = false)
public class FeedEntry implements Serializable
{
	private static final long serialVersionUID = 1150763613614458205L;

	static final String GENERATING_EVENT_FLAG = "GENERATOR";

	private Map<String, Serializable> data = new HashMap<String, Serializable>();

	@Column(updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public FeedEntry(Serializable generatingEvent, List<Serializable> metaData)
	{
		setGeneratingEvent(generatingEvent);
		for (Serializable metaDatum : metaData)
		{
			addMetaDatum(metaDatum);
		}
	}

	public FeedEntry(Serializable generatingEvent, Serializable... metaData)
	{
		this(generatingEvent, Arrays.asList(metaData));
	}

	@SuppressWarnings("unused")
	private FeedEntry()
	{
	}

	public Serializable getGeneratingEvent()
	{
		return data.get(GENERATING_EVENT_FLAG);
	}

	public Collection<Serializable> retrieveMetaData(
			Class<? extends Serializable> clazz)
	{
		List<Serializable> relevantMetaData = new ArrayList<Serializable>();

		final String typeIdentifier = makeMetaDatumTypeIdentifier(clazz);
		int index = 0;
		String key = null;

		while (data.keySet().contains(key))
		{
			relevantMetaData.add(data.get(key));
			index++;
			key = typeIdentifier + index;
		}
		return relevantMetaData;
	}

	private void setGeneratingEvent(Serializable generatingEvent)
	{
		data.put(GENERATING_EVENT_FLAG, generatingEvent);
	}

	private void addMetaDatum(Serializable metaDatum)
	{
		data.put(makeMetaDatumKey(metaDatum), metaDatum);
	}

	private String makeMetaDatumTypeIdentifier(Class<?> clazz)
	{
		return clazz.getSimpleName();
	}

	private String makeMetaDatumKey(Serializable datum)
	{
		String metaDatumTypeIdentifier = makeMetaDatumTypeIdentifier(datum
				.getClass());
		Assert.isTrue(!metaDatumTypeIdentifier
				.equals(FeedEntry.GENERATING_EVENT_FLAG));
		Integer index = 0;
		while (data.containsKey(metaDatumTypeIdentifier + index))
		{
			index++;
		}
		metaDatumTypeIdentifier += index;

		return metaDatumTypeIdentifier;
	}
}