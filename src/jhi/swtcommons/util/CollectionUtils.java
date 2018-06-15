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

/**
 * {@link CollectionUtils} contains methods to manipulate/check {@link Collection}s and certain sub-classes in particular.
 *
 * @author Sebastian Raubach
 */
public class CollectionUtils
{
	/**
	 * Joins the given input {@link Collection} with the given delimiter into a String
	 *
	 * @param input     The input {@link Collection}
	 * @param delimiter The delimiter to use
	 * @return The joined String
	 */
	public static <T> String joinList(Collection<T> input, String delimiter)
	{
		if (input == null || input.size() < 1)
			return "";

		StringBuilder builder = new StringBuilder();

		Iterator<T> it = input.iterator();

		builder.append(it.next());

		while (it.hasNext())
		{
			builder.append(delimiter).append(it.next());
		}

		return builder.toString();
	}

	/**
	 * Checks if the given {@link Collection} is either <code>null</code> or empty.
	 *
	 * @param input The {@link Collection} to check
	 * @return <code>true</code> if either <code>input == null</code> or <code>input.size() < 1</code>
	 */
	public static <T> boolean isEmpty(Collection<T> input)
	{
		return input == null || input.size() < 1;
	}

	/**
	 * Creates a {@link List} from the given input {@link String} by first splitting it on the given splitter
	 *
	 * @param input    The input {@link String}
	 * @param splitter The splitter
	 * @return The parsed {@link List}
	 */
	@SuppressWarnings("unused")
	public static List<String> parseList(String input, String splitter)
	{
		if (StringUtils.isEmpty(input))
			return new ArrayList<>();

		String[] parts = input.split(splitter);

		return new ArrayList<>(Arrays.asList(parts));
	}
}
