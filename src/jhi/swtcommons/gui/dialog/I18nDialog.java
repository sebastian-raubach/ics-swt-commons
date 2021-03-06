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
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;

import jhi.swtcommons.gui.i18n.*;

/**
 * {@link I18nDialog} extends {@link Dialog}. It overrides {@link #createButton(Composite, int, String, boolean)} and replaces the default English
 * label with the internationalized version. Labels that are different to the ones defined in {@link IDialogConstants} won't be changed.
 *
 * @author Sebastian Raubach
 */
public class I18nDialog extends Dialog
{
	protected I18nDialog(Shell parentShell)
	{
		super(parentShell);
	}

	@Override
	protected final Button createButton(Composite parent, int id, String label, boolean defaultButton)
	{
		if (IDialogConstants.ABORT_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_ABORT);
		else if (IDialogConstants.BACK_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_BACK);
		else if (IDialogConstants.CANCEL_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_CANCEL);
		else if (IDialogConstants.CLOSE_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_CLOSE);
		else if (IDialogConstants.FINISH_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_FINISH);
		else if (IDialogConstants.HELP_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_HELP);
		else if (IDialogConstants.IGNORE_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_IGNORE);
		else if (IDialogConstants.NEXT_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_NEXT);
		else if (IDialogConstants.NO_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_NO);
		else if (IDialogConstants.NO_TO_ALL_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_NO_TO_ALL);
		else if (IDialogConstants.OK_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_OK);
		else if (IDialogConstants.OPEN_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_OPEN);
		else if (IDialogConstants.PROCEED_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_PROCEED);
		else if (IDialogConstants.RETRY_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_RETRY);
		else if (IDialogConstants.SKIP_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_SKIP);
		else if (IDialogConstants.STOP_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_STOP);
		else if (IDialogConstants.YES_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_YES);
		else if (IDialogConstants.YES_TO_ALL_LABEL.equals(label))
			label = RB.getStringInternal(RB.WINDOW_BUTTON_YES_TO_ALL);

		return super.createButton(parent, id, label, defaultButton);
	}
}
