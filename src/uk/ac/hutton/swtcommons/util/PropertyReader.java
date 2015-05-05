/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package uk.ac.hutton.swtcommons.util;

import java.io.*;
import java.util.*;

/**
 * {@link PropertyReader} is a wrapper around {@link Properties} to read properties.
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class PropertyReader
{
	/** The name of the properties file (slash necessary for MacOS X) */
	protected String propertiesFile;

	protected final Properties properties = new Properties();

	public PropertyReader(String propertiesFile)
	{
		this.propertiesFile = propertiesFile;

		if (!this.propertiesFile.startsWith("/"))
			this.propertiesFile = "/" + this.propertiesFile;
	}

	/**
	 * Loads the properties {@link File}. It will try to load the local file first (in home directory). If this file doesn't exist, it will fall back
	 * to the default within the jar (or the local file in the project during development).
	 *
	 * @throws IOException Thrown if the file interaction fails
	 */
	public abstract void load() throws IOException;

	/**
	 * Parses the value of the given key and tries to split it on the given separator. Returns the {@link List} of split {@link String}s
	 *
	 * @param key       The properties key
	 * @param separator The separator
	 * @return The {@link List} of split {@link String}s
	 */
	public List<String> getPropertyListAsString(String key, String separator)
	{
		String property = properties.getProperty(key);

		List<String> result = new ArrayList<>();

		if (!StringUtils.isEmpty(property))
		{
			String[] parts = property.split(separator);

			for (String part : parts)
			{
				part = part.trim();
				if (!StringUtils.isEmpty(part))
					result.add(part);
			}
		}

		return result;
	}

	/**
	 * Sets the value of the given key to the {@link String} returned from {@link CollectionUtils#joinList(Collection, String)}.
	 *
	 * @param key       The properties key
	 * @param items     The {@link Collection} of {@link String}s
	 * @param delimiter The delimiter
	 * @see CollectionUtils#joinList(Collection, String)
	 */
	public void setPropertyList(String key, Collection<String> items, String delimiter)
	{
		set(key, CollectionUtils.joinList(items, delimiter));
	}

	protected void set(String key, String value)
	{
		if (!StringUtils.isEmpty(key))
		{
			if (StringUtils.isEmpty(value))
				properties.remove(key);
			else
				properties.put(key, value);
		}
	}

	/**
	 * Stores the {@link Parameter}s from the {@link ParameterStore} to the {@link Properties} object and then saves it using {@link
	 * Properties#store(Writer, String)}.
	 *
	 * @throws IOException Thrown if the file interaction fails
	 */
	public abstract void store() throws IOException;

	/**
	 * Reads a property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The property or <code>null</code> if the property is not found
	 */
	public String getProperty(String propertyName)
	{
		return properties.getProperty(propertyName);
	}

	/**
	 * Reads a property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The property or the fallback if the property is not found
	 */
	public String getProperty(String propertyName, String fallback)
	{
		String property = properties.getProperty(propertyName);

		return StringUtils.isEmpty(property) ? fallback : property;
	}

	/**
	 * Reads an {@link Integer} property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The {@link Integer} property
	 */
	public Integer getPropertyInteger(String propertyName)
	{
		return Integer.parseInt(getProperty(propertyName));
	}

	/**
	 * Reads an {@link Integer} property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The {@link Integer} property
	 */
	public Integer getPropertyInteger(String propertyName, int fallback)
	{
		try
		{
			return Integer.parseInt(getProperty(propertyName));
		}
		catch (Exception e)
		{
			return fallback;
		}
	}

	/**
	 * Reads an {@link Boolean} property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The {@link Boolean} property
	 */
	public Boolean getPropertyBoolean(String propertyName)
	{
		return Boolean.parseBoolean(getProperty(propertyName));
	}

	/**
	 * Reads an {@link Boolean} property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The {@link Boolean} property
	 */
	public Boolean getPropertyBoolean(String propertyName, boolean fallback)
	{
		try
		{
			return Boolean.parseBoolean(getProperty(propertyName));
		}
		catch (Exception e)
		{
			return fallback;
		}
	}

	/**
	 * Reads an {@link Long} property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The {@link Long} property
	 */
	public Long getPropertyLong(String propertyName)
	{
		return Long.parseLong(getProperty(propertyName));
	}

	/**
	 * Reads an {@link Long} property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The {@link Long} property
	 */
	public Long getPropertyLong(String propertyName, long fallback)
	{
		try
		{
			return Long.parseLong(getProperty(propertyName));
		}
		catch (Exception e)
		{
			return fallback;
		}
	}

	/**
	 * Reads an {@link Double} property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The {@link Double} property
	 */
	public Double getPropertyDouble(String propertyName)
	{
		return Double.parseDouble(getProperty(propertyName));
	}

	/**
	 * Reads an {@link Double} property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The {@link Double} property
	 */
	public Double getPropertyDouble(String propertyName, double fallback)
	{
		try
		{
			return Double.parseDouble(getProperty(propertyName));
		}
		catch (Exception e)
		{
			return fallback;
		}
	}

	/**
	 * Reads an {@link Float} property from the .properties file
	 *
	 * @param propertyName The property to read
	 * @return The {@link Float} property
	 */
	public Float getPropertyFloat(String propertyName)
	{
		return Float.parseFloat(getProperty(propertyName));
	}

	/**
	 * Reads an {@link Float} property from the .properties file. The fallback will be used if there is no such property.
	 *
	 * @param propertyName The property to read
	 * @param fallback     The value that is returned if the property isn't set
	 * @return The {@link Float} property
	 */
	public Float getPropertyFloat(String propertyName, float fallback)
	{
		try
		{
			return Float.parseFloat(getProperty(propertyName));
		}
		catch (Exception e)
		{
			return fallback;
		}
	}

	/**
	 * Reads a property from the .properties file and substitutes parameters
	 *
	 * @param propertyName The property to read
	 * @param parameters   The parameters to substitute
	 * @return The property or null if the property is not found
	 */
	public String getProperty(String propertyName, Object... parameters)
	{
		String property = properties.getProperty(propertyName);
		if (parameters.length > 0)
			return String.format(property, parameters);
		else
			return property;
	}
}
