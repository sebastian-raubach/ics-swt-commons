/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.gui.viewer;

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
	 * Returns the mapping of license name (for display purposes) and the path to the file (relative path).
	 * <p>
	 * As an example, this could be something like: <ul> <li>Apache Commons Logging -> ./licence/apache-commons-logging.txt</li> <li>My fancy license
	 * -> fancy.txt</li> </ul>
	 * <p>
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
