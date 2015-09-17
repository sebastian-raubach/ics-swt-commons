/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
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
