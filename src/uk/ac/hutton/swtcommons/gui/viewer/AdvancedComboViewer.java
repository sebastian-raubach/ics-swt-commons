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
import uk.ac.hutton.swtcommons.util.*;

import java.util.*;

/**
 * {@link AdvancedComboViewer} extends {@link ComboViewer} and adds convenient methods like {@link #getSelectedItem()}
 *
 * @param <T> The type to display
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class AdvancedComboViewer<T> extends ComboViewer
{

	public AdvancedComboViewer(Composite parent, int style)
	{
		super(parent, style | SWT.READ_ONLY);
		this.setContentProvider(ArrayContentProvider.getInstance());
	}

	/**
	 * Selects the item with the given display text. The display text is obtained from {@link #getDisplayText(Object)}
	 *
	 * @param displayText The display text of the item to select
	 */
	@SuppressWarnings("unchecked")
	public void selectItem(String displayText)
	{
		if (StringUtils.isEmpty(displayText))
			return;

		if (getSelectedItem() != null && getDisplayText(getSelectedItem()).equals(displayText))
			return;

		if (this.getInput() == null)
			return;

		((Collection<T>) this.getInput()).stream().filter(item -> displayText.equals(getDisplayText(item))).forEach(item -> {
			setSelection(new StructuredSelection(item));
		});
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
		if (selection == null || selection.size() < 1)
			return null;
		else
			return (T) selection.getFirstElement();
	}

	protected abstract String getDisplayText(T item);
}
