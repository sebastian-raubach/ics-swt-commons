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

package jhi.swtcommons.gui.viewer;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import java.util.*;

import jhi.swtcommons.util.*;

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
	 * @return <code>true</code> if the requested item is selected (no matter if already selected or manually selected)
	 */
	@SuppressWarnings("unchecked")
	public boolean selectItem(String displayText)
	{
		if (StringUtils.isEmpty(displayText))
			return false;

		if (getSelectedItem() != null && Objects.equals(getDisplayText(getSelectedItem()), displayText))
			return true;

		if (this.getInput() == null)
			return false;

		for (T object : (Collection<T>) getInput())
		{
			if (displayText.equals(getDisplayText(object)))
			{
				setSelection(new StructuredSelection(object));
				return true;
			}
		}

		return false;

//		((Collection<T>) this.getInput()).stream()
//										 .filter(item -> displayText.equals(getDisplayText(item)))
//										 .forEach(item -> setSelection(new StructuredSelection(item)));
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
