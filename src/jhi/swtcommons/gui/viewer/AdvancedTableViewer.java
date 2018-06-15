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
