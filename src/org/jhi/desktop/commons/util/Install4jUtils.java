/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at germinate@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.util;

import com.install4j.api.launcher.*;
import com.install4j.api.update.*;

import java.io.*;
import java.net.*;
import java.util.*;

import scri.commons.gui.*;

/**
 * @author Sebastian Raubach
 */
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

		private UpdateInterval(String resourceId)
		{
			this.resourceId = resourceId;
		}

		public String getResourceId()
		{
			return resourceId;
		}
	}

	public static String VERSION;

	// Toolkit details
	private boolean isSWT = false;

	// Application details
	private static String appID, updateID;
	private static String defaultVersionNumber = "x.x.x";

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
		this.appID = appID;
		this.updateID = updateID;
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

	public void setSWT(boolean isSWT)
	{
		this.isSWT = isSWT;
	}

	/**
	 * install4j update check. This will only work when running under the full
	 * install4j environment, so expect exceptions everywhere else
	 */
	public void doStartUpCheck(Class jarClass)
	{
		getVersion(jarClass);
		pingServer();

		Runnable r = new Runnable()
		{
			public void run()
			{
				checkForUpdate();
			}
		};

		try
		{
			if (isSWT)
				new Thread(r).start();
			else
				javax.swing.SwingUtilities.invokeAndWait(r);
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

			if (UpdateScheduleRegistry.checkAndReset() == false)
			{
				return;
			}

			UpdateDescriptor ud = UpdateChecker.getUpdateDescriptor(updaterURL, ApplicationDisplayMode.GUI);

			if (ud.getPossibleUpdateEntry() != null)
			{
				checkForUpdate(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Error e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Shows the install4j updater app to check for updates and download/install
	 * any that are found.
	 */
	public static void checkForUpdate(boolean block)
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

	public static void setDefaultVersionNumber(String defaultVersionNumber)
	{
		Install4jUtils.defaultVersionNumber = defaultVersionNumber;
	}

	private void pingServer()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				try
				{
					// Safely encode the URL's parameters
					String id = URLEncoder.encode(userID, "UTF-8");
					String version = URLEncoder.encode(VERSION, "UTF-8");
					String locale = URLEncoder.encode("" + Locale.getDefault(), "UTF-8");
					String os = URLEncoder.encode(System.getProperty("os.name")
							+ " (" + System.getProperty("os.arch") + ")", "UTF-8");
					String user = URLEncoder.encode(System.getProperty("user.name"), "UTF-8");

					String addr = trackerURL + "?id=" + id + "&version="
							+ version + "&locale=" + locale + "&rating="
							+ userRating + "&os=" + os;

					// We ONLY log usernames from HUTTON-DUNDEE-LAN addresses
					if (SystemUtils.isSCRIUser())
						addr += "&user=" + user;

					// Nudges the cgi script to log the fact that a version of
					// Flapjack has been run
					URL url = new URL(addr);
					HttpURLConnection c = (HttpURLConnection) url.openConnection();

					c.getResponseCode();
					c.disconnect();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};

		// We run this in a separate thread to avoid any waits due to lack of an
		// internet connection or the server being non-responsive
		new Thread(r).start();
	}
}
