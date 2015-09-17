/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package jhi.swtcommons.gui;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.*;
import jhi.swtcommons.gui.i18n.*;
import jhi.swtcommons.gui.layout.*;
import jhi.swtcommons.util.*;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * A {@link RestartableApplication} is a simple SWT application that provides restart functionality. If used correctly, this will result in a complete
 * reset of the application.
 * <p>
 * Child classes <b>have to</b> set up their content in {@link #onStart()}
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class RestartableApplication
{
	private static final String SHELL_ID = UUID.randomUUID().toString();
	protected final Shell   shell;
	protected final Display display;

	private PropertyReader propertyReader;

	public RestartableApplication()
	{
		display = new Display();
		shell = new Shell();
		initResources();

		propertyReader = getPropertyReader();
		try
		{
			propertyReader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		onPreStart();

        /* Listen for the close event to release resources */
		display.addFilter(SWT.Close, event ->
		{
				/* Only shut down if it's the main shell that's being closed */
			if (SHELL_ID.equals(event.widget.getData()))
				shutdown();
		});

		addDropListener();

		onRestart();

        /* Layout */
		GridLayoutUtils.useDefault().marginHeight(0).marginWidth(0).applyTo(shell);
		GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_BOTH).applyTo(shell);

		shell.pack();
		ShellUtils.applySize(shell);
		ShellUtils.setMinSize(shell, getMinWidth(), getMinHeight());
		shell.setData(SHELL_ID);
		shell.open();

		onPostOpen();

		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Returns the minimum width of the Shell
	 *
	 * @return The minimum width of the Shell
	 */
	protected int getMinWidth()
	{
		return ShellUtils.MIN_WIDTH;
	}

	/**
	 * Returns the minimum height of the Shell
	 *
	 * @return The minimum height of the Shell
	 */
	protected int getMinHeight()
	{
		return ShellUtils.MIN_HEIGHT;
	}

	/**
	 * Called After {@link #shell}, {@link #display}, {@link #initResources()} and {@link #propertyReader} have been initialized
	 */
	protected void onPreStart()
	{
	}

	/**
	 * Called after {@link Shell#open()} has been called
	 */
	protected void onPostOpen()
	{
	}

	/**
	 * Adds the OS specific quit menu item with the given {@link Listener}
	 *
	 * @param name     The name of the menu item (not used on OSX)
	 * @param parent   The parent {@link Menu} (not used on OSX)
	 * @param listener The {@link Listener} to add
	 */
	protected void addQuitMenuItemListener(String name, Menu parent, Listener listener)
	{
		addMenuItem(name, parent, e -> {
			/* Quit is a special case. We need to prevent the default and then call the listener */
			e.doit = false;
			listener.handleEvent(e);
		}, SWT.ID_QUIT);
	}

	/**
	 * Adds the OS specific about menu item with the given {@link Listener}
	 *
	 * @param name     The name of the menu item (not used on OSX)
	 * @param parent   The parent {@link Menu} (not used on OSX)
	 * @param listener The {@link Listener} to add
	 */
	protected void addAboutMenuItemListener(String name, Menu parent, Listener listener)
	{
		addMenuItem(name, parent, listener, SWT.ID_ABOUT);
	}

	/**
	 * Adds the OS specific preferences menu item with the given {@link Listener}
	 *
	 * @param name     The name of the menu item (not used on OSX)
	 * @param parent   The parent {@link Menu} (not used on OSX)
	 * @param listener The {@link Listener} to add
	 */
	protected void addPreferencesMenuItemListener(String name, Menu parent, Listener listener)
	{
		addMenuItem(name, parent, listener, SWT.ID_PREFERENCES);
	}

	/**
	 * Convenience method that takes care of special menu items (About, Preferences, Quit)
	 *
	 * @param name     The name of the menu item
	 * @param parent   The parent {@link Menu}
	 * @param listener The {@link Listener} to add to the item
	 * @param id       The <code>SWT.ID_*</code> id
	 */
	private void addMenuItem(String name, Menu parent, Listener listener, int id)
	{
		if (OSUtils.isMac())
		{
			Menu systemMenu = Display.getDefault().getSystemMenu();

			for (MenuItem systemItem : systemMenu.getItems())
			{
				if (systemItem.getID() == id)
				{
					systemItem.addListener(SWT.Selection, listener);
					return;
				}
			}
		}

		/* We get here if we're not running on a Mac, or if we're running on a Mac, but the menu item with the given id hasn't been found */
		MenuItem item = new MenuItem(parent, SWT.NONE);
		item.setText(name);
		item.addListener(SWT.Selection, listener);
	}

	/**
	 * Handles file drop events
	 * <p>
	 * Calls {@link #onFileDropped(List)} if there is at least one dropped file
	 */
	private void addDropListener()
	{
		DropTarget dt = new DropTarget(shell, DND.DROP_DEFAULT | DND.DROP_MOVE);
		dt.setTransfer(new Transfer[]{FileTransfer.getInstance()});
		dt.addDropListener(new DropTargetAdapter()
		{
			public void drop(DropTargetEvent event)
			{
				/* Make sure that we have the focus */
				shell.forceActive();

                /* Get the list of files */
				String fileList[] = new String[0];
				FileTransfer ft = FileTransfer.getInstance();
				if (ft.isSupportedType(event.currentDataType))
				{
					fileList = (String[]) event.data;
				}

                /* Check if they exist and are actually files. If so, add the
				 * File object */
				List<File> files = new ArrayList<>();

				for (String filename : fileList)
				{
					File file = new File(filename);

					if (file.exists() && file.isFile())
						files.add(file);
				}

				if (!CollectionUtils.isEmpty(files))
					onFileDropped(files);
			}
		});
	}

	/**
	 * Returns the application specific {@link PropertyReader}
	 *
	 * @return The application specific {@link PropertyReader}
	 */
	protected abstract PropertyReader getPropertyReader();

	/**
	 * Causes the application to restart.
	 * <p>
	 * This includes: <ul> <li>Calling {@link PropertyReader#store()}</li> <li>Calling {@link ParameterStore#clear()}</li> <li>Calling {@link
	 * PropertyReader#load()}</li> <li>Calling {@link RB#reset()}</li> <li>Calling {@link RestartableApplication#onStart()}</li> </ul>
	 */
	protected void onRestart()
	{
		try
		{
			propertyReader.store();
			CommonParameterStore.getInstance().clear();
			propertyReader.load();
		}
		catch (IOException e)
		{
			shutdown();
		}

		RB.reset();

		onStart();

		shell.layout(true);
	}

	/**
	 * Shuts the application down
	 */
	protected void shutdown()
	{
		onExit();

		disposeResources();
		try
		{
			propertyReader.store();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			/* Do nothing here, since we're already shutting down */
		}
		System.exit(0);
	}

	/**
	 * Called when files are dragged and dropped on the Shell
	 *
	 * @param files The dropped files
	 */
	protected void onFileDropped(List<File> files)
	{

	}

	/**
	 * Called when the application starts or restarts.
	 * <p>
	 * Create all contents here.
	 */
	protected abstract void onStart();

	/**
	 * Called just before the application exits
	 */
	protected abstract void onExit();

	/**
	 * Called to initialize all required resources
	 * <p>
	 * Sub-classes have to initialize all required resources
	 */
	protected abstract void initResources();

	/**
	 * Called to dispose of all resources
	 * <p>
	 * Sub-classes have to dispose of all resources
	 */
	protected abstract void disposeResources();
}
