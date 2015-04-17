/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.util;

import org.eclipse.swt.widgets.*;

@SuppressWarnings("unused")
public class WidgetUtils
{
	/**
	 * Disposes the given {@link Widget} by calling {@link Widget#dispose()} after checking that it's not <code>null</code> and that it isn't already
	 * disposed ({@link Widget#isDisposed()}).
	 *
	 * @param widget The {@link Widget} to dispose
	 */
	public static void dispose(Widget widget)
	{
		if (widget != null && !widget.isDisposed())
			widget.dispose();
	}

	/**
	 * Disposes the given {@link Widget}s by calling {@link Widget#dispose()} after checking that they aren not <code>null</code> and that they aren't
	 * already disposed ({@link Widget#isDisposed()}).
	 *
	 * @param widgets The {@link Widget}s to dispose
	 */
	public static void dispose(Widget... widgets)
	{
		if (widgets == null)
			return;

		for (Widget widget : widgets)
			dispose(widget);
	}
}
