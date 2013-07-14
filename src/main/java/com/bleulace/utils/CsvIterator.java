package com.bleulace.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

/**
 * Reads csv files from META-INF/csv dir, which is where we keep csv of random
 * names and email addresses for demo and testing purposes
 * 
 * This is quick and dirty.
 * 
 * @author Arleigh Dickerson
 * 
 * @param <T>
 *            target bean class
 */
public class CsvIterator<T> implements Iterator<T>, Iterable<T>
{
	private T next;

	private final Class<T> clazz;

	private CsvDozerBeanReader reader;

	private final CellProcessor[] cellProcessors;

	/**
	 * 
	 * @param clazz
	 *            the bean to instantiate using file data as params for setters
	 * @param fieldMapping
	 *            , @see http://supercsv.sourceforge.net/examples_dozer.html
	 * @param cellProcessors
	 * @see supercsv docs
	 * @param csvFileTitle
	 *            with trailing ".csv" omitted. The file to be read must be in
	 *            the META-INF/csv dir
	 */
	public CsvIterator(Class<T> clazz, String[] fieldMapping,
			CellProcessor[] cellProcessors, String csvFileTitle)
	{
		this.cellProcessors = cellProcessors;
		this.clazz = clazz;
		try
		{
			String path = "META-INF/csv/" + csvFileTitle + ".csv";
			InputStream iStream = this.getClass().getClassLoader()
					.getResourceAsStream(path);
			Reader streamReader = new InputStreamReader(iStream);
			reader = new CsvDozerBeanReader(streamReader,
					CsvPreference.STANDARD_PREFERENCE);
			reader.getHeader(true);
			reader.configureBeanMapping(clazz, fieldMapping);
			cycle();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNext()
	{
		return reader != null && next != null;
	}

	@Override
	public T next()
	{
		try
		{
			return cycle();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remove()
	{
	}

	private T cycle() throws Exception
	{
		T next = this.next;
		try
		{
			this.next = reader.read(clazz, cellProcessors);
		}
		catch (IOException e)
		{
			this.next = null;
			reader.close();
		}
		return next;
	}

	@Override
	public Iterator<T> iterator()
	{
		return this;
	}

	public Class<T> getClazz()
	{
		return clazz;
	}
}