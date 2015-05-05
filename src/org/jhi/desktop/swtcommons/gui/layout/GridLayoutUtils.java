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

import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * {@link GridLayoutUtils} is a utility class using the fluent interface code style to create {@link GridLayout} objects
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class GridLayoutUtils
{
	private GridLayout layout;

	/**
	 * Creates a new instance of {@link GridLayoutUtils} with a basic {@link GridLayout}
	 *
	 * @see GridLayout#GridLayout()
	 */
	private GridLayoutUtils()
	{
		layout = new GridLayout();
	}

	/**
	 * Creates a new instance of {@link GridLayoutUtils} with a basic {@link GridLayout}
	 *
	 * @param numColumns            The number of columns
	 * @param makeColumnsEqualWidth Should the columns be of equal width
	 * @see GridLayout#GridLayout(int, boolean)
	 */
	private GridLayoutUtils(int numColumns, boolean makeColumnsEqualWidth)
	{
		layout = new GridLayout(numColumns, makeColumnsEqualWidth);
	}

	/**
	 * Creates a new instance of {@link GridLayoutUtils} based on the given {@link GridLayout}
	 *
	 * @param layout The {@link GridLayout} to use for this {@link GridLayoutUtils}
	 */
	private GridLayoutUtils(GridLayout layout)
	{
		this.layout = layout;
	}

	/**
	 * Creates a new instance of {@link GridLayoutUtils} using the SWT default values.
	 *
	 * @return <code>this</code>
	 */
	public static GridLayoutUtils useDefault()
	{
		return new GridLayoutUtils();
	}

	/**
	 * Creates a new instance of {@link GridLayoutUtils} using the given values.
	 *
	 * @return <code>this</code>
	 */
	public static GridLayoutUtils useValues(int numColumns, boolean makeColumnsEqualWidth)
	{
		return new GridLayoutUtils(numColumns, makeColumnsEqualWidth);
	}

	/**
	 * Sets the left margin of the {@link GridLayout}
	 *
	 * @param marginLeft The left margin
	 * @return <code>this</code>
	 * @see GridLayout#marginLeft
	 */
	public GridLayoutUtils marginLeft(int marginLeft)
	{
		layout.marginLeft = marginLeft;
		return this;
	}

	/**
	 * Sets the right margin of the {@link GridLayout}
	 *
	 * @param marginRight The right margin
	 * @return <code>this</code>
	 * @see GridLayout#marginRight
	 */
	public GridLayoutUtils marginRight(int marginRight)
	{
		layout.marginRight = marginRight;
		return this;
	}

	/**
	 * Sets the top margin of the {@link GridLayout}
	 *
	 * @param marginTop The top margin
	 * @return <code>this</code>
	 * @see GridLayout#marginTop
	 */
	public GridLayoutUtils marginTop(int marginTop)
	{
		layout.marginTop = marginTop;
		return this;
	}

	/**
	 * Sets the bottom margin of the {@link GridLayout}
	 *
	 * @param marginBottom The bottom margin
	 * @return <code>this</code>
	 * @see GridLayout#marginBottom
	 */
	public GridLayoutUtils marginBottom(int marginBottom)
	{
		layout.marginBottom = marginBottom;
		return this;
	}

	/**
	 * Sets the margin height of the {@link GridLayout}
	 *
	 * @param marginHeight The margin height
	 * @return <code>this</code>
	 * @see GridLayout#marginHeight
	 */
	public GridLayoutUtils marginHeight(int marginHeight)
	{
		layout.marginHeight = marginHeight;
		return this;
	}

	/**
	 * Sets the margin width of the {@link GridLayout}
	 *
	 * @param marginWidth The margin width
	 * @return <code>this</code>
	 * @see GridLayout#marginWidth
	 */
	public GridLayoutUtils marginWidth(int marginWidth)
	{
		layout.marginWidth = marginWidth;
		return this;
	}

	/**
	 * Sets the horizontal spacing of the {@link GridLayout}
	 *
	 * @param horizontalSpacing The horizontal spacing
	 * @return <code>this</code>
	 * @see GridLayout#horizontalSpacing
	 */
	public GridLayoutUtils horizontalSpacing(int horizontalSpacing)
	{
		layout.horizontalSpacing = horizontalSpacing;
		return this;
	}

	/**
	 * Sets the vertical spacing of the {@link GridLayout}
	 *
	 * @param verticalSpacing The vertical spacing
	 * @return <code>this</code>
	 * @see GridLayout#verticalSpacing
	 */
	public GridLayoutUtils verticalSpacing(int verticalSpacing)
	{
		layout.verticalSpacing = verticalSpacing;
		return this;
	}

	/**
	 * Sets the boolean deciding if the columns should be of equal width of the {@link GridLayout}
	 *
	 * @param makeColumnsEqualWidth Should the columns be of equal width
	 * @return <code>this</code>
	 * @see GridLayout#makeColumnsEqualWidth
	 */
	public GridLayoutUtils makeColumnsEqualWidth(boolean makeColumnsEqualWidth)
	{
		layout.makeColumnsEqualWidth = makeColumnsEqualWidth;
		return this;
	}

	/**
	 * Sets the number of columns of the {@link GridLayout}
	 *
	 * @param numColumns The number of columns
	 * @return <code>this</code>
	 * @see GridLayout#numColumns
	 */
	public GridLayoutUtils numColumns(int numColumns)
	{
		layout.numColumns = numColumns;
		return this;
	}

	/**
	 * Returns the {@link GridLayout}
	 *
	 * @return The {@link GridLayout}
	 */
	public GridLayout create()
	{
		return layout;
	}

	/**
	 * Applies the {@link GridLayout} to the given {@link Composite}
	 *
	 * @param composite The {@link Composite} to apply the {@link GridLayout} to
	 */
	public void applyTo(Composite composite)
	{
		composite.setLayout(layout);
	}
}
