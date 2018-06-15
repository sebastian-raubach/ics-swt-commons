/*
 *  Copyright 2018 Information and Computational Sciences,
 *  The James Hutton Institute.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package jhi.swtcommons.util;

import java.util.*;
import java.util.concurrent.*;

import javax.activation.*;

/**
 * {@link ParameterStore} is the central instance holding the {@link Parameter}s of the current state.
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class ParameterStore
{
	protected final Map<Parameter, Object> STATE_PARAMETERS = new ConcurrentHashMap<>();

	/**
	 * Get a specific {@link Parameter} from the {@link ParameterStore}
	 *
	 * @param key The {@link Parameter} identifier
	 * @return The {@link Parameter} or <code>null</code>
	 */
	public Object get(Parameter key)
	{
		return STATE_PARAMETERS.get(key);
	}

	/**
	 * Gets a specific {@link Parameter} from the {@link ParameterStore}. Makes sure that it is in {@link String} form. This will, e.g., convert a
	 * {@link List} to a csv String.
	 *
	 * @param key The {@link Parameter} identifier
	 * @return The {@link Parameter} or <code>null</code>
	 */
	public String getAsString(Parameter key)
	{
		if (get(key) == null)
			return null;
		else if (key.getType().equals(List.class))
			return CollectionUtils.joinList((List<?>) get(key), ",");
		else if (key.getType().equals(Install4jUtils.UpdateInterval.class))
			return ((Install4jUtils.UpdateInterval) get(key)).name();
		else
			return get(key).toString();
	}

	/**
	 * Gets a specific {@link Parameter} from the {@link ParameterStore}. Makes sure that it is in {@link String} form. This will, e.g., convert a
	 * {@link List} to a csv String.
	 *
	 * @param key      The {@link Parameter} identifier
	 * @param fallback The value that is returned if the requested {@link Parameter} doesn't have a value
	 * @return The {@link Parameter} or <code>null</code>
	 */
	public String getAsString(Parameter key, String fallback)
	{
		if (get(key) == null)
			return fallback;
		else
			return getAsString(key);
	}

	/**
	 * Set a specific {@link Parameter} to the {@link ParameterStore}
	 *
	 * @param key   The {@link Parameter} identifier
	 * @param value The actual {@link Parameter} value
	 * @return the previous value associated with key, or null if there was no mapping for the key. (A null return can also indicate that the map
	 * previously associated null with the key)
	 */
	public Object put(Parameter key, Object value)
	{
		if (value == null)
			return STATE_PARAMETERS.remove(key);
		else
			return STATE_PARAMETERS.put(key, value);
	}

	/**
	 * Set a specific {@link Parameter} to the {@link ParameterStore}
	 *
	 * @param key   The {@link Parameter} identifier
	 * @param value The actual {@link Parameter} value
	 * @param type  The {@link Class} of this {@link Parameter}
	 * @return the previous value associated with key, or null if there was no mapping for the key. (A null return can also indicate that the map
	 * previously associated null with the key)
	 * @throws UnsupportedDataTypeException Thrown if the given type is not supported
	 */
	public Object put(Parameter key, String value, Class<?> type) throws UnsupportedDataTypeException
	{
		if (type.equals(Integer.class))
		{
			return STATE_PARAMETERS.put(key, Integer.parseInt(value));
		}
		else if (type.equals(Double.class))
		{
			return STATE_PARAMETERS.put(key, Double.parseDouble(value));
		}
		else if (type.equals(Float.class))
		{
			return STATE_PARAMETERS.put(key, Float.parseFloat(value));
		}
		else if (type.equals(Boolean.class))
		{
			return STATE_PARAMETERS.put(key, Boolean.parseBoolean(value));
		}
		else if (type.equals(String.class))
		{
			return STATE_PARAMETERS.put(key, value);
		}
		else
			throw new UnsupportedDataTypeException();
	}

	/**
	 * Returns true if the {@link Parameter} store contains a mapping for the specified key.
	 *
	 * @param key The {@link Parameter} identifier
	 * @return <code>true</code> if the {@link Parameter} store contains a mapping for the specified key.
	 */
	public boolean containsKey(Parameter key)
	{
		return STATE_PARAMETERS.containsKey(key);
	}

	/**
	 * Removes the mapping for a key from this map if it is present (optional operation).
	 *
	 * @param key The {@link Parameter} identifier
	 * @return The previous value associated with key, or <code>null</code> if there was no mapping for the key.
	 */
	public Object remove(Parameter key)
	{
		return STATE_PARAMETERS.remove(key);
	}

	/**
	 * Removes all of the mappings from the {@link ParameterStore}. The {@link ParameterStore} will be empty after this call returns.
	 */
	public void clear()
	{
		STATE_PARAMETERS.clear();
	}
}
