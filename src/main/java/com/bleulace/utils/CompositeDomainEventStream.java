package com.bleulace.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * A composite domain event stream.
 * 
 * @see DomainEventStream
 * @author Arleigh Dickerson
 * 
 */
public class CompositeDomainEventStream implements DomainEventStream
{
	/**
	 * the component streams of this composite.
	 */
	private final DomainEventStream[] streams;

	/**
	 * Represents the date of the last event acquired from this stream.
	 */
	private DateTime cursor = new LocalDateTime(0).toDateTime();

	/**
	 * 
	 * @param streams
	 *            the streams to create this composite from
	 */
	public CompositeDomainEventStream(DomainEventStream... streams)
	{
		this.streams = streams;
	}

	/**
	 * 
	 * @param streams
	 *            the streams to create this composite from
	 */
	public CompositeDomainEventStream(Collection<DomainEventStream> streams)
	{
		Set<DomainEventStream> streamList = new HashSet<DomainEventStream>(
				streams);
		this.streams = streamList.toArray(new DomainEventStream[streamList
				.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext()
	{
		return acquireStream() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public DomainEventMessage next()
	{
		DomainEventMessage message = acquireStream().next();
		cursor = message.getTimestamp();
		return message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public DomainEventMessage peek()
	{
		return hasNext() ? acquireStream().peek() : null;
	}

	/**
	 * Acquires the "next" stream in line. The "next" stream is the stream whose
	 * next event timestamp has the smallest field difference (delta) when
	 * compared to the cursor.
	 * 
	 * @return
	 */
	private DomainEventStream acquireStream()
	{
		DomainEventStream currentCandidate = null;
		int delta = 0;

		for (DomainEventStream stream : streams)
		{
			if (stream.hasNext())
			{
				if (currentCandidate == null || delta(currentCandidate) < delta)
				{
					currentCandidate = stream;
					delta = delta(stream);
				}
			}
		}
		return currentCandidate;
	}

	/**
	 * calculates the field difference, in millis, between the stream's next
	 * event and the cursor
	 * 
	 * @param stream
	 *            the stream to calculate delta for
	 * @return field difference in millis
	 */
	private int delta(DomainEventStream stream)
	{
		return Period.fieldDifference(cursor.toLocalDate(),
				stream.peek().getTimestamp().toLocalDate()).getMillis();
	}
}