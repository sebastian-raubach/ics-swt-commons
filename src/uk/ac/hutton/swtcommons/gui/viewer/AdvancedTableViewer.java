/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package uk.ac.hutton.swtcommons.gui.viewer;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

/**
 * {@link AdvancedTableViewer} extends {@link TableViewer} and adds convenient methods like {@link #getSelectedItem()}
 *
 * @param <T> The type to display
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class AdvancedTableViewer<T> extends TableViewer
{

	public AdvancedTableViewer(Composite parent, int style)
	{
		super(parent, style | SWT.READ_ONLY);
		this.setContentProvider(ArrayContentProvider.getInstance());
	}

	/**
	 * Returns the selected {@link T}
	 *
	 * @return The selected {@link T}
	 */
	@SuppressWarnings("unchecked")
	public T getSelectedItem()
	{
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		return (T) selection.getFirstElement();
	}
}
