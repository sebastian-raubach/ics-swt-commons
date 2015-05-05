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

/**
 * {@link StringUtils} contains methods to manipulate/check {@link String}s.
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class StringUtils
{
	/**
	 * Checks if the given {@link String} is either <code>null</code> or empty after calling {@link String#trim()}.
	 *
	 * @param input The {@link String} to check
	 * @return <code>true</code> if the given {@link String} is <code>null</code> or empty after calling {@link String#trim()}.
	 */
	public static boolean isEmpty(String input)
	{
		return input == null || input.trim().isEmpty();
	}

	/**
	 * Checks if the given {@link String}s are either <code>null</code> or empty after calling {@link String#trim()}.
	 *
	 * @param input The {@link String}s to check
	 * @return <code>true</code> if any of the given {@link String}s is <code>null</code> or empty after calling {@link String#trim()}.
	 */
	public static boolean isEmpty(String... input)
	{
		if (input == null)
			return true;
		for (String text : input)
		{
			if (isEmpty(text))
				return true;
		}
		return false;
	}

	/**
	 * Joins the given parts to a String separated by the delimiter. {@link #isEmpty(String)} will be called on each part and the part will only be
	 * added if the result is <code>true</code>.
	 *
	 * @param delimiter The delimiter to use
	 * @param parts     The parts to join
	 * @return The joined parts or an empty {@link String} if either <code>parts.length == 0</code> or there is no part that returns
	 * <code>false</code> for {@link #isEmpty(String)}
	 */
	public static String join(String delimiter, String... parts)
	{
		if (parts.length == 0)
			return "";

		StringBuilder builder = new StringBuilder();

		boolean atLeastOne = false;
		for (String part : parts)
		{
			if (!isEmpty(part))
			{
				atLeastOne = true;
				builder.append(part).append(delimiter);
			}
		}

        /* Remove the last delimiter. We have to do it this way, because we
		 * don't know how many (if any) parts pass the check */
		if (atLeastOne)
		{
			int startIndex = builder.lastIndexOf(delimiter);

			if (startIndex != -1)
				builder.delete(startIndex, startIndex + delimiter.length());
		}

		return builder.toString();
	}

	/**
	 * Checks if the given parts are pairwise equal, i.e. calls {@link String#equals(Object)} on each adjacent pair.
	 *
	 * @param parts The parts to compare
	 * @return <code>true</code> if all parts are equal, <code>false</code> otherwise and also if one part is <code>null</code>
	 */
	public static boolean areEqual(String... parts)
	{
		for (int i = 0; i < parts.length - 1; i++)
		{
			if (parts[i] == null)
				return false;
			if (!parts[i].equals(parts[i + 1]))
				return false;
		}

		return true;
	}
}
