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

package jhi.swtcommons.gui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import jhi.swtcommons.gui.layout.*;

/**
 * {@link BannerDialog} is a {@link Dialog} that adds a Germinate banner to the top.
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public abstract class BannerDialog extends I18nDialog
{
	private Color color;
	private Image image;

	protected BannerDialog(Shell parentShell, Image image, Color color)
	{
		super(parentShell);
		this.image = image;
		this.color = color;
	}

	@Override
	protected final Control createDialogArea(Composite parent)
	{
		final Composite composite = (Composite) super.createDialogArea(parent);

        /* LOGO */
		Composite logoComp = new Composite(composite, SWT.NONE);
		logoComp.setBackground(color);
		Label logoLabel = new Label(logoComp, SWT.NONE);
		logoLabel.setBackground(color);
		logoLabel.setImage(image);

		GridLayoutUtils.useDefault().marginWidth(0).marginHeight(0).applyTo(composite);
		GridLayoutUtils.useDefault().marginWidth(10).marginHeight(10).applyTo(logoComp);

		GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_BOTH).applyTo(composite);
		GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_TOP).applyTo(logoComp);
		GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.CENTER_TOP).applyTo(logoLabel);

		createContent(new Composite(composite, SWT.NONE));

		return composite;
	}

	/**
	 * Creates the content of the {@link BannerDialog}, i.e. the part below the germinate banner
	 *
	 * @param parent The {@link Composite} to add the content to
	 */
	protected abstract void createContent(Composite parent);
}
