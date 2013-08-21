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

import org.axonframework.domain.MetaData;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

@Embeddable
@RooJavaBean(settersByDefault = false)
public class FeedEntry implements Serializable
{
	static final String GENERATING_EVENT_FLAG = "GENERATOR";
	static final String METADATA_FLAG = "METADATA";

	private Map<String, Serializable> data = new HashMap<String, Serializable>();

	@Column(updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public FeedEntry(Serializable generatingEvent, MetaData metaData,
			List<Serializable> data)
	{
		setGeneratingEvent(generatingEvent);
		setMetaData(metaData);
		for (Serializable datum : data)
		{
			addDatum(datum);
		}
	}

	public FeedEntry(Serializable generatingEvent, MetaData metaData,
			Serializable... data)
	{
		this(generatingEvent, metaData, Arrays.asList(data));
	}

	private FeedEntry()
	{
	}

	public Serializable getGeneratingEvent()
	{
		return data.get(GENERATING_EVENT_FLAG);
	}

	public MetaData getMetaData()
	{
		return (MetaData) data.get(METADATA_FLAG);
	}

	public Collection<Serializable> retrieveMetaData(
			Class<? extends Serializable> clazz)
	{
		List<Serializable> relevantMetaData = new ArrayList<Serializable>();

		final String typeIdentifier = makeDatumTypeIdentifier(clazz);
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

	@Override
	public FeedEntry clone()
	{
		FeedEntry f = new FeedEntry();
		f.data = data;
		f.dateCreated = dateCreated;
		return f;
	}

	private void setGeneratingEvent(Serializable generatingEvent)
	{
		data.put(GENERATING_EVENT_FLAG, generatingEvent);
	}

	private void setMetaData(MetaData metaData)
	{
		data.put(METADATA_FLAG, metaData);
	}

	private void addDatum(Serializable metaDatum)
	{
		data.put(makeMetaDatumKey(metaDatum), metaDatum);
	}

	private String makeDatumTypeIdentifier(Class<?> clazz)
	{
		return clazz.getSimpleName();
	}

	private String makeMetaDatumKey(Serializable datum)
	{
		String metaDatumTypeIdentifier = makeDatumTypeIdentifier(datum
				.getClass());
		Assert.isTrue(!metaDatumTypeIdentifier
				.equals(FeedEntry.GENERATING_EVENT_FLAG));
		Assert.isTrue(!metaDatumTypeIdentifier.equals(FeedEntry.METADATA_FLAG));
		Integer index = 0;
		while (data.containsKey(metaDatumTypeIdentifier + index))
		{
			index++;
		}
		metaDatumTypeIdentifier += index;

		return metaDatumTypeIdentifier;
	}
}