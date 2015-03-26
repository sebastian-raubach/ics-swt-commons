/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at germinate@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.gui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.jhi.desktop.commons.gui.layout.*;

/**
 * {@link BannerDialog} is a {@link Dialog} that adds a Germinate
 * banner to the top.
 * 
 * @author Sebastian Raubach
 *
 */
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
        Label germinateLogo = new Label(logoComp, SWT.NONE);
        germinateLogo.setBackground(color);
        germinateLogo.setImage(image);

        GridLayoutUtils.useDefault().marginWidth(0).marginHeight(0).applyTo(composite);
        GridLayoutUtils.useDefault().marginWidth(10).marginHeight(10).applyTo(logoComp);

        GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_BOTH).applyTo(composite);
        GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.FILL_TOP).applyTo(logoComp);
        GridDataUtils.usePredefined(GridDataUtils.GridDataStyle.CENTER_TOP).applyTo(germinateLogo);

        createContent(new Composite(composite, SWT.NONE));

        return composite;
    }

    /**
     * Creates the content of the {@link BannerDialog}, i.e. the part
     * below the germinate banner
     * 
     * @param parent
     *            The {@link Composite} to add the content to
     */
    protected abstract void createContent(Composite parent);
}
