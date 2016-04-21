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

import com.install4j.api.launcher.*;
import com.install4j.api.update.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class Install4jUtils
{
	public enum UpdateInterval
	{
		NEVER("update.interval.never"),
		STARTUP("update.interval.startup"),
		DAILY("update.interval.daily"),
		WEEKLY("update.interval.weekly"),
		MONTHLY("update.interval.monthly");

		private String resourceId;

		UpdateInterval(String resourceId)
		{
			this.resourceId = resourceId;
		}

		public String getResource()
		{
			return jhi.swtcommons.gui.i18n.RB.getStringInternal(resourceId);
		}
	}

	public static String VERSION;

	// Application details
	private static String appID, updateID;
	private static String defaultVersionNumber = "x.xx.xx.xx";

	private Callback callback = null;

	// User details
	private UpdateInterval updateSchedule;
	private String         userID;
	private int            userRating;

	// URL Details
	private String updaterURL, trackerURL;


	/**
	 * @param appID    ID as seen by Install4j, eg 9483-2571-4596-9336
	 * @param updateID install4j ID for the GUI update window, eg 65
	 */
	public Install4jUtils(String appID, String updateID)
	{
		Install4jUtils.appID = appID;
		Install4jUtils.updateID = updateID;
	}

	public void setUser(UpdateInterval updateSchedule, String userID, int userRating)
	{
		this.updateSchedule = updateSchedule;
		this.userID = userID;
		this.userRating = userRating;
	}

	public void setURLs(String updaterURL, String trackerURL)
	{
		this.updaterURL = updaterURL;
		this.trackerURL = trackerURL;
	}

	public void setCallback(Callback callback)
	{
		this.callback = callback;
	}

	/**
	 * install4j update check. This will only work when running under the full install4j environment, so expect exceptions everywhere else
	 */
	public void doStartUpCheck(Class jarClass)
	{
		getVersion(jarClass);
		pingServer();

		Runnable r = this::checkForUpdate;

		try
		{
			new Thread(r).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void checkForUpdate()
	{
		try
		{
			switch (updateSchedule)
			{
				case STARTUP:
					UpdateScheduleRegistry.setUpdateSchedule(UpdateSchedule.ON_EVERY_START);
					break;
				case DAILY:
					UpdateScheduleRegistry.setUpdateSchedule(UpdateSchedule.DAILY);
					break;
				case WEEKLY:
					UpdateScheduleRegistry.setUpdateSchedule(UpdateSchedule.WEEKLY);
					break;
				case MONTHLY:
					UpdateScheduleRegistry.setUpdateSchedule(UpdateSchedule.MONTHLY);
					break;

				default:
					UpdateScheduleRegistry.setUpdateSchedule(UpdateSchedule.NEVER);
			}

			if (!UpdateScheduleRegistry.checkAndReset())
			{
				return;
			}

			UpdateDescriptor ud = UpdateChecker.getUpdateDescriptor(updaterURL, ApplicationDisplayMode.GUI);

			boolean newVersion = ud.getPossibleUpdateEntry() != null;

			if (newVersion)
			{
				checkForUpdate(true);
			}

			if (callback != null)
				callback.onUpdateAvailableStatus(newVersion);
		}
		catch (Exception | Error e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Shows the install4j updater app to check for updates and download/install any that are found.
	 */
	public void checkForUpdate(boolean block)
	{
		try
		{
			ApplicationLauncher.launchApplication(updateID, null, block, null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getVersion(Class jarClass)
	{
		// Attempt to get the version string from the jar's manifest
		VERSION = jarClass.getPackage().getImplementationVersion();

		// If it's not found, we must be running the development version
		if (VERSION == null)
			VERSION = defaultVersionNumber;

		return VERSION;
	}

	public void setDefaultVersionNumber(String defaultVersionNumber)
	{
		Install4jUtils.defaultVersionNumber = defaultVersionNumber;
	}

	private void pingServer()
	{
		Runnable r = () -> {
			try
			{
				StringBuilder builder = new StringBuilder();

				/* Safely encode the URL parameters */
				builder.append(trackerURL)
					   .append("?id=")
					   .append(URLEncoder.encode(userID, "UTF-8"))
					   .append("&version=")
					   .append(URLEncoder.encode(VERSION, "UTF-8"))
					   .append("&locale=")
					   .append(URLEncoder.encode("" + Locale.getDefault(), "UTF-8"))
					   .append("&rating=")
					   .append(userRating)
					   .append("&os=")
					   .append(URLEncoder.encode(System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")", "UTF-8"));

				/* We ONLY log usernames from HUTTON-DUNDEE-LAN addresses */
				if (isSCRIUser())
					builder.append("&user=")
						   .append(URLEncoder.encode(System.getProperty("user.name"), "UTF-8"));

				/* Nudges the cgi script to log the fact that a version of the application has been run */
				URL url = new URL(builder.toString());
				HttpURLConnection c = (HttpURLConnection) url.openConnection();

				c.getResponseCode();
				c.disconnect();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		};

		/* We run this in a separate thread to avoid any waits due to lack of an internet connection or the server being non-responsive */
		new Thread(r).start();
	}

	private static boolean isSCRIUser()
	{
		try
		{
			Enumeration e = NetworkInterface.getNetworkInterfaces();

			while (e != null && e.hasMoreElements())
			{
				Enumeration e2 = ((NetworkInterface) e.nextElement()).getInetAddresses();

				while (e2.hasMoreElements())
				{
					String addr = ((InetAddress) e2.nextElement()).getHostAddress();
					if (addr.startsWith("143.234.96.")
							|| addr.startsWith("143.234.97.")
							|| addr.startsWith("143.234.98.")
							|| addr.startsWith("143.234.99.")
							|| addr.startsWith("143.234.100.")
							|| addr.startsWith("143.234.101."))
					{
						return true;
					}
				}
			}
		}
		catch (Exception e)
		{
		}

		return false;
	}

	public interface Callback
	{
		void onUpdateAvailableStatus(boolean available);
	}
}
