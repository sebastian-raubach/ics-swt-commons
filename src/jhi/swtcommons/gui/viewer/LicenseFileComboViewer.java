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

/**
 * {@link LicenseFileComboViewer} extends {@link AdvancedComboViewer} and is used to display the available licenses
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class LicenseFileComboViewer extends AdvancedComboViewer<String>
{
	private LinkedHashMap<String, String> licenseData;

	public LicenseFileComboViewer(Composite parent, int style)
	{
		super(parent, style | SWT.READ_ONLY);

		licenseData = getLicenseData();

		this.addSelectionChangedListener(e -> {
					String key = getSelectedItem();
					String value = licenseData.get(key);
					showLicense(key, value);
				}
		);
	}

	public void init()
	{
		setInput(licenseData.keySet());

		if (!licenseData.isEmpty())
			setSelection(new StructuredSelection(getElementAt(0)));
	}

	@Override
	protected String getDisplayText(String s)
	{
		return s;
	}

	/**
	 * Returns the mapping of license name (for display purposes) and the path to the file (relative path). <p> As an example, this could be something
	 * like: <ul> <li>Apache Commons Logging -> ./licence/apache-commons-logging.txt</li> <li>My fancy license -> fancy.txt</li> </ul> <p>
	 *
	 * @return The mapping of license name (for display purposes) and the path to the file (relative path).
	 */
	protected abstract LinkedHashMap<String, String> getLicenseData();

	/**
	 * Is called when the user selects an element
	 *
	 * @param text The display text (license name)
	 * @param path The relative path to the file
	 */
	protected abstract void showLicense(String text, String path);
}
