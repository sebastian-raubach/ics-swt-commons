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

import org.eclipse.swt.program.*;

import java.io.*;

/**
 * {@link OSUtils} contains constants and utility methods used for operating
 * system specific tasks.
 * 
 * @author Sebastian Raubach
 * 
 */
public class OSUtils
{
    /**
     * {@link OSConstants} contains helper constants for {@link OperatingSystem}
     * 
     * @author Sebastian Raubach
     * 
     */
    private interface OSConstants
    {
        public static final int    x64      = 64;
        public static final int    x86      = 32;
        public static final int    xUNKNOWN = -1;

        public static final String LINUX    = "linux_gtk";
        public static final String OSX      = "macosx";
        public static final String WINDOWS  = "win32";
        public static final String UNKNOWN  = "unknown";
    }

    /**
     * {@link OperatingSystem} contains all supported OS's and {@link #UNKNOWN}
     * for the rest.
     * 
     * @author Sebastian Raubach
     * 
     */
    public static enum OperatingSystem
    {
        LINUX_64(OSConstants.LINUX, OSConstants.x64),
        MACOS_64(OSConstants.OSX, OSConstants.x64),
        WINDOWS_64(OSConstants.WINDOWS, OSConstants.x64),
        LINUX_32(OSConstants.LINUX, OSConstants.x86),
        MACOS_32(OSConstants.OSX, OSConstants.x86),
        WINDOWS_32(OSConstants.WINDOWS, OSConstants.x86),
        UNKNOWN(OSConstants.UNKNOWN, OSConstants.xUNKNOWN);

        private String name;
        private int    bits;

        private OperatingSystem(String name, int bits)
        {
            this.name = name;
            this.bits = bits;
        }

        /**
         * Returns the name of the {@link OperatingSystem}
         *
         * @return The name of the {@link OperatingSystem}
         */
        public String getName()
        {
            return name;
        }

        /**
         * Returns the bit part of the {@link OperatingSystem}
         *
         * @return The bit part of the {@link OperatingSystem}
         */
        public int getBits()
        {
            return bits;
        }

        /**
         * Returns the bit {@link String} of the {@link OperatingSystem}
         *
         * @return The bit {@link String} of the {@link OperatingSystem}
         */
        public String getBitString()
        {
            return "x" + bits;
        }

        /**
         * Returns the {@link OperatingSystem} based on the given OS name and OS
         * architecture.
         *
         * @param osName
         *            The OS name as can be obtained from
         *            {@link System#getProperty(String)} using "os.name"
         * @param osArch
         *            The OS architecture as can be obtained from
         *            {@link System#getProperty(String)} using "os.arch"
         * @return The matching {@link OperatingSystem}
         */
        public static OperatingSystem getOS(String osName, String osArch)
        {
            osName = osName.toLowerCase();
            osArch = osArch.toLowerCase();
            if (osName.contains("win"))
            {
                if (osArch.contains("64"))
                    return WINDOWS_64;
                else
                    return WINDOWS_32;
            }
            else if (osName.contains("mac"))
            {
                if (osArch.contains("64"))
                    return MACOS_64;
                else
                    return MACOS_32;
            }
            else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"))
            {
                if (osArch.contains("64"))
                    return LINUX_64;
                else
                    return LINUX_32;
            }
            else
                return UNKNOWN;
        }
    }

    /** The host system's {@link OperatingSystem} */
    private static final OperatingSystem OS           = OperatingSystem.getOS(System.getProperty("os.name"), System.getProperty("os.arch"));
    /** The host system's file separator (as obtained from {@link File#separator} */
    public static final  String          OS_DELIMITER = File.separator;

    /**
     * Checks if the OS is Windows
     *
     * @return <code>true</code> if the OS is Windows
     */
    public static boolean isWindows()
    {
        return OS == OperatingSystem.WINDOWS_32 || OS == OperatingSystem.WINDOWS_64;
    }

    /**
     * Checks if the OS is MacOS
     *
     * @return <code>true</code> if the OS is MacOS
     */
    public static boolean isMac()
    {
        return OS == OperatingSystem.MACOS_32 || OS == OperatingSystem.MACOS_64;
    }

    /**
     * Checks if the OS is Unix
     *
     * @return <code>true</code> if the OS is Unix
     */
    public static boolean isUnix()
    {
        return OS == OperatingSystem.LINUX_32 || OS == OperatingSystem.LINUX_64;
    }

    /**
     * opens the given address with the system's default program
     *
     * @param address
     *            The address to open (web address, email address, ...)
     */
    public static void open(String address)
    {
        Program.launch(address);
    }
}
