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

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * {@link ShellUtils} contains methods to manipulate {@link Shell} objects
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class ShellUtils
{
	private static final float WIDTH_FACTOR  = 0.3f;
	private static final float HEIGHT_FACTOR = 0.43f;
	public static final int   MIN_WIDTH     = 650;
	public static final int   MIN_HEIGHT    = 450;

	/**
	 * Applies a default size to the given {@link Shell}. The size is based on
	 * {@link #WIDTH_FACTOR} and {@link #HEIGHT_FACTOR} multiplied with the
	 * dimensions obtained from {@link Display#getPrimaryMonitor()} and
	 * {@link Monitor#getBounds()}.
	 * <p>
	 * The minimal size will be {@link #MIN_WIDTH} x {@link #MIN_HEIGHT}
	 * <p>
	 * The maximal size will be equal to the dimensions of the current monitor
	 *
	 * @param shell The {@link Shell} to apply the new dimensions to
	 */
	public static void applySize(Shell shell)
	{
		/* Get the current monitors dimensions */
		Rectangle screen = Display.getCurrent().getPrimaryMonitor().getBounds();

        /* Calculate width: MIN_WIDTH <= width <= screen.width */
		int width = Math.min(screen.width, Math.max(MIN_WIDTH, (int) (screen.width * WIDTH_FACTOR)));
        /* Calculate height: MIN_HEIGHT <= height <= screen.height */
		int height = Math.min(screen.height, Math.max(MIN_HEIGHT, (int) (screen.height * HEIGHT_FACTOR)));

		shell.setSize(width, height);
	}

	/**
	 * Sets the minimal size to the shell by calling
	 * {@link Shell#setMinimumSize(int, int)} the following values:
	 * <ul>
	 * <li>x: The minimum of <code>minWidth</code> and the
	 * available screen width</li>
	 * <li>y: The minimum of <code>minHeight</code> and
	 * the available screen height</li>
	 * </ul>
	 *
	 * @param shell     The {@link Shell}
	 * @param minWidth  The minimal width
	 * @param minHeight The minimal height
	 */
	public static void setMinSize(Shell shell, int minWidth, int minHeight)
	{
        /* Get the current monitors dimensions */
		Rectangle screen = Display.getCurrent().getPrimaryMonitor().getBounds();

        /* Calculate width: minWidth <= width <= screen.width */
		int width = Math.min(screen.width, minWidth);
        /* Calculate height: minHeight <= height <= screen.height */
		int height = Math.min(screen.height, minHeight);

		shell.setMinimumSize(width, height);
	}

	/**
	 * Returns the top left coordinates of a window with the given size centered
	 * to the given parent.
	 * <p>
	 * The returned coordinates will never be outwith the display.
	 *
	 * @param parent   The parent {@link Shell} to center to
	 * @param thisSize The size of the component to center
	 * @return The coordinates of the top left corner of the centered component
	 */
	public static Point getLocationCenteredTo(Shell parent, Point thisSize)
	{
		Rectangle bounds = parent.getBounds();

		Rectangle display = Display.getDefault().getBounds();

		int x = bounds.x + ((bounds.width - thisSize.x) / 2);
		int y = bounds.y + ((bounds.height - thisSize.y) / 2);

		x = Math.max(0, Math.min(x, display.x + display.width));
		y = Math.max(0, Math.min(y, display.y + display.height));

		return new Point(x, y);
	}
}
