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
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

import jhi.swtcommons.util.*;

/**
 * {@link AdvancedListViewer} extends {@link ListViewer} and adds convenient methods like {@link #getSelectedItems()}
 *
 * @param <T> The type to display
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class AdvancedListViewer<T> extends ListViewer
{

	public AdvancedListViewer(Composite parent, int style)
	{
		super(parent, style);
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

		List<T> selectedItems = getSelectedItems();

		if (!CollectionUtils.isEmpty(selectedItems) && getDisplayText(selectedItems.get(0)).equals(displayText))
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
	public List<T> getSelectedItems()
	{
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		if (selection == null || selection.size() < 1)
			return null;
		else
			return new ArrayList<>(selection.toList());
	}

	protected abstract String getDisplayText(T item);
}
