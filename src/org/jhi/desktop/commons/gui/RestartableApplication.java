/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at germinate@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.gui;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.*;
import org.jhi.desktop.commons.gui.i18n.*;
import org.jhi.desktop.commons.gui.layout.*;
import org.jhi.desktop.commons.util.*;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * A {@link RestartableApplication} is a simple SWT application that provides
 * restart functionality. If used correctly, this will result in a complete
 * reset of the application.
 * <p/>
 * Child classes <b>have to</b> set up their content in {@link #onStart()}
 *
 * @author Sebastian Raubach
 */
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

		onPreStart();

        /* Listen for the close event to release resources */
		display.addFilter(SWT.Close, new Listener()
		{
			@Override
			public void handleEvent(Event e)
			{
				/* Only shut down if it's the main shell that's being closed */
				if (SHELL_ID.equals(e.widget.getData()))
					shutdown();
			}
		});

		addDropListener();

		onRestart();

        /* Layout */
		GridLayoutUtils.useDefault().marginHeight(0).marginWidth(0).applyTo(shell);
		GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_BOTH).applyTo(shell);

		shell.pack();
		ShellUtils.applySize(shell);
		ShellUtils.setMinSize(shell);
		shell.setData(SHELL_ID);
		shell.open();

		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	protected void onPreStart()
	{
	}

	/**
	 * Handles file drop events
	 * <p/>
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

	protected abstract PropertyReader getPropertyReader();

	/**
	 * Causes the application to restart.
	 * <p/>
	 * This includes:
	 * <ul>
	 * <li>Calling {@link PropertyReader#store()}</li>
	 * <li>Calling {@link ParameterStore#clear()}</li>
	 * <li>Calling {@link PropertyReader#load()}</li>
	 * <li>Calling {@link RB#reset()}</li>
	 * <li>Calling {@link RestartableApplication#onStart()}</li>
	 * </ul>
	 */
	protected void onRestart()
	{
		try
		{
			propertyReader.store();
			ParameterStore.clear();
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
	 * <p/>
	 * Create all contents here.
	 */
	protected abstract void onStart();

	/**
	 * Called just before the application exits
	 */
	protected abstract void onExit();

	protected abstract void initResources();

	protected abstract void disposeResources();
}
