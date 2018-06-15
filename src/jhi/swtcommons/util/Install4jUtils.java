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

import com.install4j.api.launcher.*;
import com.install4j.api.update.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

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
		Logger.getLogger("").log(Level.INFO, "Version: " + VERSION);
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
			Logger.getLogger("").log(Level.INFO, "Update schedule: " + updateSchedule);
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
				Logger.getLogger("").log(Level.INFO, "Update: not the time to update yet");
				return;
			}

			Logger.getLogger("").log(Level.INFO, "URL: " + updaterURL);
			UpdateDescriptor ud = UpdateChecker.getUpdateDescriptor(updaterURL, ApplicationDisplayMode.GUI);

			boolean newVersion = ud.getPossibleUpdateEntry() != null;

			Logger.getLogger("").log(Level.INFO, "Has new version: " + newVersion);

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
				if(!StringUtils.isEmpty(trackerURL))
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
					Logger.getLogger("").log(Level.INFO, "Pinging: " + url);
					HttpURLConnection c = (HttpURLConnection) url.openConnection();

					c.getResponseCode();
					c.disconnect();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		};

		/* We run this in a separate thread to avoid any waits due to lack of an internet connection or the server being non-responsive */
		new Thread(r).start();
	}

	/*
	 * Returns true if the VM detects that it is running on an IP address known to be in use by SCRI.
	 * @return true if the VM detects that it is running on an IP address known to be in use by SCRI
	 */
	private static boolean isSCRIUser()
	{
		try
		{
			/* Need to check over all network interfaces (LAN/wireless/etc) to try and find a match... */
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces != null && interfaces.hasMoreElements())
			{
				/* And each interface can have multiple IPs... */
				Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
				while (addresses.hasMoreElements())
				{
					if (addresses.nextElement().getHostAddress().startsWith("143.234."))
						return true;
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
