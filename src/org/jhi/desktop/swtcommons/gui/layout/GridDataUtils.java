/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.gui.layout;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * {@link GridDataUtils} is a utility class using the fluent interface code style to create {@link GridData} objects
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class GridDataUtils
{
	public enum GridDataStyle
	{
		FILL_BOTH(SWT.FILL, SWT.FILL, true, true),
		FILL_CENTER(SWT.FILL, SWT.CENTER, true, false),
		BEGINNING_TOP(SWT.BEGINNING, SWT.TOP, true, false),
		BEGINNING_CENTER(SWT.BEGINNING, SWT.CENTER, true, false),
		BEGINNING_CENTER_FALSE(SWT.BEGINNING, SWT.CENTER, false, false),
		END_CENTER(SWT.END, SWT.CENTER, true, false),
		END_CENTER_FALSE(SWT.END, SWT.CENTER, false, false),
		END_BOTTOM(SWT.END, SWT.BOTTOM, true, false),
		RIGHT_TOP(SWT.RIGHT, SWT.TOP, false, false),
		RIGHT_FILL(SWT.RIGHT, SWT.FILL, false, true),
		FILL_TOP(SWT.FILL, SWT.TOP, true, false),
		FILL_BOTTOM(SWT.FILL, SWT.BOTTOM, true, true),
		FILL_BOTTOM_FALSE(SWT.FILL, SWT.BOTTOM, true, false),
		CENTER_BOTH(SWT.CENTER, SWT.CENTER, true, true),
		CENTER_BOTH_FALSE(SWT.CENTER, SWT.CENTER, false, false),
		CENTER_TOP(SWT.CENTER, SWT.TOP, true, false),
		CENTER_BOTTOM(SWT.CENTER, SWT.BOTTOM, true, false),
		CENTER_BOTTOM_FALSE(SWT.CENTER, SWT.BOTTOM, false, false);

		private int     horizontalAlignment;
		private int     verticalAlignment;
		private boolean grabExcessHorizontalSpace;
		private boolean grabExcessVerticalSpace;

		GridDataStyle(int horizontalAlignment, int verticalAlignment, boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace)
		{
			this.horizontalAlignment = horizontalAlignment;
			this.verticalAlignment = verticalAlignment;
			this.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
			this.grabExcessVerticalSpace = grabExcessVerticalSpace;
		}
	}

	private GridData data;
	private boolean alreadyApplied = false;

	/**
	 * Creates a new instance of {@link GridDataUtils} with a basic {@link GridData}
	 *
	 * @see GridData#GridData()
	 */
	private GridDataUtils()
	{
		data = new GridData();
	}

	/**
	 * Creates a new instance of {@link GridDataUtils} with a basic {@link GridData}
	 *
	 * @param style The {@link GridData} style
	 * @see GridData#GridData(int)
	 */
	private GridDataUtils(int style)
	{
		data = new GridData(style);
	}

	/**
	 * Creates a new instance of {@link GridDataUtils} with a basic {@link GridData}
	 *
	 * @param width  A minimum width for the column
	 * @param height A minimum height for the row
	 * @see GridData#GridData(int, int)
	 */
	private GridDataUtils(int width, int height)
	{
		data = new GridData(width, height);
	}

	/**
	 * Creates a new instance of {@link GridDataUtils} with a basic {@link GridData}
	 *
	 * @param horizontalAlignment       How the {@link Control} will be positioned horizontally within a cell, one of: {@link SWT#BEGINNING} (or
	 *                                  {@link SWT#LEFT}), {@link SWT#CENTER}, {@link SWT#END} (or {@link SWT#RIGHT}), or {@link SWT#FILL}
	 * @param verticalAlignment         How the {@link Control} will be positioned vertically within a cell, one of: {@link SWT#BEGINNING} (or {@link
	 *                                  SWT#TOP}), {@link SWT#CENTER}, {@link SWT#END} (or {@link SWT#BOTTOM}), or {@link SWT#FILL}
	 * @param grabExcessHorizontalSpace Whether the cell will be made wide enough to fit the remaining horizontal space
	 * @param grabExcessVerticalSpace   Whether the cell will be made high enough to fit the remaining vertical space
	 * @see GridData#GridData(int, int, boolean, boolean)
	 */
	private GridDataUtils(int horizontalAlignment, int verticalAlignment, boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace)
	{
		data = new GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace, grabExcessVerticalSpace);
	}

	/**
	 * Creates a new instance of {@link GridDataUtils} with a basic {@link GridData}
	 *
	 * @param horizontalAlignment       How the {@link Control} will be positioned horizontally within a cell, one of: {@link SWT#BEGINNING} (or
	 *                                  {@link SWT#LEFT}), {@link SWT#CENTER}, {@link SWT#END} (or {@link SWT#RIGHT}), or {@link SWT#FILL}
	 * @param verticalAlignment         How the {@link Control} will be positioned vertically within a cell, one of: {@link SWT#BEGINNING} (or {@link
	 *                                  SWT#TOP}), {@link SWT#CENTER}, {@link SWT#END} (or {@link SWT#BOTTOM}), or {@link SWT#FILL}
	 * @param grabExcessHorizontalSpace Whether the cell will be made wide enough to fit the remaining horizontal space
	 * @param grabExcessVerticalSpace   Whether the cell will be made high enough to fit the remaining vertical space
	 * @param horizontalSpan            The number of column cells that the control will take up
	 * @param verticalSpan              The number of row cells that the control will take up
	 * @see GridData#GridData(int, int, boolean, boolean, int, int)
	 */
	private GridDataUtils(int horizontalAlignment, int verticalAlignment, boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace, int horizontalSpan, int verticalSpan)
	{
		data = new GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace, grabExcessVerticalSpace, horizontalSpan, verticalSpan);
	}

	/**
	 * Creates a new instance of {@link GridDataUtils} based on the given {@link GridData}
	 */
	private GridDataUtils(GridData data)
	{
		this.data = data;
	}

	/**
	 * Creates a new {@link GridDataUtils} using the SWT default values
	 */
	public static GridDataUtils useDefault()
	{
		return new GridDataUtils();
	}

	/**
	 * Creates a new {@link GridDataUtils} instance based on the predefined {@link GridDataStyle}s
	 *
	 * @param style One of the predefined {@link GridDataStyle}s
	 */
	public static GridDataUtils usePredefined(GridDataStyle style)
	{
		if (style == null)
			return new GridDataUtils();
		else
			return new GridDataUtils(style.horizontalAlignment, style.verticalAlignment, style.grabExcessHorizontalSpace, style.grabExcessVerticalSpace);
	}

	/**
	 * Specifies if the {@link GridData} should grab excessive horizontal space
	 *
	 * @param grabExcessHorizontalSpace <code>true</code> if the {@link GridData} should grab excessive horizontal space
	 * @return <code>this</code>
	 * @see GridData#grabExcessHorizontalSpace
	 */
	public GridDataUtils grabExcessHorizontalSpace(boolean grabExcessHorizontalSpace)
	{
		data.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		return this;
	}

	/**
	 * Specifies if the {@link GridData} should grab excessive vertical space
	 *
	 * @param grabExcessVerticalSpace <code>true</code> if the {@link GridData} should grab excessive vertical space
	 * @return <code>this</code>
	 * @see GridData#grabExcessVerticalSpace
	 */
	public GridDataUtils grabExcessVerticalSpace(boolean grabExcessVerticalSpace)
	{
		data.grabExcessVerticalSpace = grabExcessVerticalSpace;
		return this;
	}

	/**
	 * Sets the height hint of the {@link GridData}
	 *
	 * @param heightHint The height hint
	 * @return <code>this</code>
	 * @see GridData#heightHint
	 */
	public GridDataUtils heightHint(int heightHint)
	{
		data.heightHint = heightHint;
		return this;
	}

	/**
	 * Sets the horizontal alignment of the {@link GridData}
	 *
	 * @param horizontalAlignment The horizontal alignment
	 * @return <code>this</code>
	 * @see GridData#horizontalAlignment
	 */
	public GridDataUtils horizontalAlignment(int horizontalAlignment)
	{
		data.horizontalAlignment = horizontalAlignment;
		return this;
	}

	/**
	 * Sets the horizontal indent of the {@link GridData}
	 *
	 * @param horizontalIndent The horizontal indent
	 * @return <code>this</code>
	 * @see GridData#horizontalIndent
	 */
	public GridDataUtils horizontalIndent(int horizontalIndent)
	{
		data.horizontalIndent = horizontalIndent;
		return this;
	}

	/**
	 * Sets the horizontal span of the {@link GridData}
	 *
	 * @param horizontalSpan The horizontal span
	 * @return <code>this</code>
	 * @see GridData#horizontalSpan
	 */
	public GridDataUtils horizontalSpan(int horizontalSpan)
	{
		data.horizontalSpan = horizontalSpan;
		return this;
	}

	/**
	 * Sets the minimum height of the {@link GridData}
	 *
	 * @param minimumHeight The minimum height
	 * @return <code>this</code>
	 * @see GridData#minimumHeight
	 */
	public GridDataUtils minimumHeight(int minimumHeight)
	{
		data.minimumHeight = minimumHeight;
		return this;
	}

	/**
	 * Sets the minimum width of the {@link GridData}
	 *
	 * @param minimumWidth The minimum width
	 * @return <code>this</code>
	 * @see GridData#minimumWidth
	 */
	public GridDataUtils minimumWidth(int minimumWidth)
	{
		data.minimumWidth = minimumWidth;
		return this;
	}

	/**
	 * Sets the vertical alignment of the {@link GridData}
	 *
	 * @param verticalAlignment The vertical alignment
	 * @return <code>this</code>
	 * @see GridData#verticalAlignment
	 */
	public GridDataUtils verticalAlignment(int verticalAlignment)
	{
		data.verticalAlignment = verticalAlignment;
		return this;
	}

	/**
	 * Sets the vertical indent of the {@link GridData}
	 *
	 * @param verticalIndent The vertical indent
	 * @return <code>this</code>
	 * @see GridData#verticalIndent
	 */
	public GridDataUtils verticalIndent(int verticalIndent)
	{
		data.verticalIndent = verticalIndent;
		return this;
	}

	/**
	 * Sets the vertical span of the {@link GridData}
	 *
	 * @param verticalSpan The vertical span
	 * @return <code>this</code>
	 * @see GridData#verticalSpan
	 */
	public GridDataUtils verticalSpan(int verticalSpan)
	{
		data.verticalSpan = verticalSpan;
		return this;
	}

	/**
	 * Sets the width hint of the {@link GridData}
	 *
	 * @param widthHint The width hint
	 * @return <code>this</code>
	 * @see GridData#widthHint
	 */
	public GridDataUtils widthHint(int widthHint)
	{
		data.widthHint = widthHint;
		return this;
	}

	/**
	 * Should the {@link Control} this {@link GridData} is applied to be excluded from the parent's {@link Layout}?
	 *
	 * @param exclude Should the {@link Control} this {@link GridData} is applied to be excluded from the parent's {@link Layout}?
	 * @return <code>this</code>
	 * @see GridData#exclude
	 */
	public GridDataUtils exclude(boolean exclude)
	{
		data.exclude = exclude;
		return this;
	}

	/**
	 * Returns the {@link GridData}
	 *
	 * @return The {@link GridData}
	 */
	public GridData create()
	{
		return createCopy();
	}

	/**
	 * Creates a deep copy of the current {@link GridData}
	 *
	 * @return The deep copy
	 */
	private GridData createCopy()
	{
		GridData newData = new GridData(data.horizontalAlignment, data.verticalAlignment, data.grabExcessHorizontalSpace, data.grabExcessVerticalSpace, data.horizontalSpan, data.verticalSpan);
		newData.exclude = data.exclude;
		newData.heightHint = data.heightHint;
		newData.horizontalIndent = data.horizontalIndent;
		newData.minimumHeight = data.minimumHeight;
		newData.minimumWidth = data.minimumWidth;
		newData.verticalIndent = data.verticalIndent;
		newData.widthHint = data.widthHint;

		return newData;
	}

	/**
	 * Applies the {@link GridData} to the given {@link Control}
	 *
	 * @param composite The {@link Control} to apply the {@link GridData} to
	 */
	public void applyTo(Control composite)
	{
		if (alreadyApplied)
		{
			composite.setLayoutData(createCopy());
		}
		else
		{
			alreadyApplied = true;
			composite.setLayoutData(data);
		}
	}
}

