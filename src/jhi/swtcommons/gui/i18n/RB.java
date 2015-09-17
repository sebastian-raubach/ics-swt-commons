/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package jhi.swtcommons.gui.i18n;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class RB
{
	private static ResourceBundle BUNDLE_INTERNAL;
	private static ResourceBundle BUNDLE;

	public static final String WINDOW_BUTTON_ABORT      = "window.button.abort";
	public static final String WINDOW_BUTTON_BACK       = "window.button.back";
	public static final String WINDOW_BUTTON_CANCEL     = "window.button.cancel";
	public static final String WINDOW_BUTTON_CLOSE      = "window.button.close";
	public static final String WINDOW_BUTTON_FINISH     = "window.button.finish";
	public static final String WINDOW_BUTTON_HELP       = "window.button.help";
	public static final String WINDOW_BUTTON_IGNORE     = "window.button.ignore";
	public static final String WINDOW_BUTTON_NEXT       = "window.button.next";
	public static final String WINDOW_BUTTON_NO         = "window.button.no";
	public static final String WINDOW_BUTTON_NO_TO_ALL  = "window.button.no.to.all";
	public static final String WINDOW_BUTTON_OK         = "window.button.ok";
	public static final String WINDOW_BUTTON_OPEN       = "window.button.open";
	public static final String WINDOW_BUTTON_PROCEED    = "window.button.proceed";
	public static final String WINDOW_BUTTON_RETRY      = "window.button.retry";
	public static final String WINDOW_BUTTON_SKIP       = "window.button.skip";
	public static final String WINDOW_BUTTON_STOP       = "window.button.stop";
	public static final String WINDOW_BUTTON_YES        = "window.button.yes";
	public static final String WINDOW_BUTTON_YES_TO_ALL = "window.button.yes.to.all";

	public static final String INFORMATION_GENERAL_TITLE = "information.general.title";
	public static final String WARNING_GENERAL_TITLE     = "warning.general.title";
	public static final String ERROR_GENERAL_TITLE       = "error.general.title";
	public static final String CONFIRM_GENERAL_TITLE     = "confirm.general.title";

	public static void reset()
	{
		BUNDLE = ResourceBundle.getBundle("messages", new UTF8Control());
		BUNDLE_INTERNAL = ResourceBundle.getBundle("messages-internal", new UTF8Control());
	}

	/**
	 * Returns the {@link String} from the {@link ResourceBundle} with optional parameter substitution using {@link MessageFormat#format(String,
	 * Object...)}.
	 *
	 * @param key       The key of the resource
	 * @param arguments The arguments to substitute (optional)
	 * @return The {@link String} from the {@link ResourceBundle} with optionally substituted parameters
	 * @see ResourceBundle#getString(String)
	 * @see MessageFormat#format(String, Object...)
	 */
	@SuppressWarnings("unused")
	public static String getString(String key, Object... arguments)
	{
		if (BUNDLE == null)
			reset();

		String result = BUNDLE.getString(key);

		// if (arguments != null && arguments.length > 0)
		result = MessageFormat.format(result, arguments);

		return result;
	}

	/**
	 * Returns the {@link String} from the {@link ResourceBundle} with optional parameter substitution using {@link MessageFormat#format(String,
	 * Object...)}.
	 *
	 * @param key       The key of the resource
	 * @param arguments The arguments to substitute (optional)
	 * @return The {@link String} from the {@link ResourceBundle} with optionally substituted parameters
	 * @see ResourceBundle#getString(String)
	 * @see MessageFormat#format(String, Object...)
	 */
	public static String getStringInternal(String key, Object... arguments)
	{
		if (BUNDLE_INTERNAL == null)
			reset();

		String result = BUNDLE_INTERNAL.getString(key);

		// if (arguments != null && arguments.length > 0)
		result = MessageFormat.format(result, arguments);

		return result;
	}

	/**
	 * Makes sure to read the properties file in UTF-8
	 *
	 * @author Sebastian Raubach
	 */
	private static class UTF8Control extends ResourceBundle.Control
	{
		public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException
		{
			// The below is a copy of the default implementation.
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, "properties");
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload)
			{
				URL url = loader.getResource(resourceName);
				if (url != null)
				{
					URLConnection connection = url.openConnection();
					if (connection != null)
					{
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			}
			else
			{
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null)
			{
				try
				{
					// Only this line is changed to make it to read properties
					// files as UTF-8.
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
				}
				finally
				{
					stream.close();
				}
			}
			return bundle;
		}
	}
}
