/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package jhi.swtcommons.util;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import jhi.swtcommons.gui.i18n.*;

/**
 * {@link DialogUtils} contains methods to create {@link MessageDialog}s. All {@link MessageDialog}s are opened on the main thread, which makes
 * calling these methods from any thread safe.
 * <p>
 * This is accomplished my utilizing {@link Display#asyncExec(Runnable)}.
 *
 * @author Sebastian Raubach
 */
@SuppressWarnings("unused")
public class DialogUtils
{
	/**
	 * Shows an information dialog using {@link MessageDialog#openInformation(Shell, String, String)}. {@link RB#INFORMATION_GENERAL_TITLE} is used as
	 * the title
	 *
	 * @param message The message content
	 * @see MessageDialog#openInformation(Shell, String, String)
	 */
	public static void showInformation(String message)
	{
		Display.getDefault().asyncExec(() -> MessageDialog.openInformation(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.INFORMATION_GENERAL_TITLE), message));
	}

	/**
	 * Shows an error dialog using {@link MessageDialog#openError(Shell, String, String)}. {@link RB#ERROR_GENERAL_TITLE} is used as the title
	 *
	 * @param message The message content
	 * @see MessageDialog#openError(Shell, String, String)
	 */
	public static void showError(String message)
	{
		Display.getDefault().asyncExec(() -> MessageDialog.openError(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.ERROR_GENERAL_TITLE), message));
	}

	/**
	 * Shows an error dialog using {@link MessageDialogWithToggle#openError(Shell, String, String, String, boolean,
	 * org.eclipse.jface.preference.IPreferenceStore, String)} . {@link RB#ERROR_GENERAL_TITLE} is used as the title. The toggle state is initially
	 * set to <code>true</code>.
	 *
	 * @param message       The message content
	 * @param toggleMessage The toggle state message
	 * @param callback      The {@link DialogCallback} that is called with the result
	 * @see MessageDialogWithToggle#openError(Shell, String, String, String, boolean, org.eclipse.jface.preference.IPreferenceStore, String)
	 */
	public static void showErrorToggle(String message, String toggleMessage, DialogCallback callback)
	{
		Display.getDefault().asyncExec(() -> {
			MessageDialogWithToggle dialog = MessageDialogWithToggle.openError(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.ERROR_GENERAL_TITLE), message, toggleMessage, true, null,
					null);

			if (callback != null)
				callback.onResult(dialog.getToggleState());
		});
	}

	/**
	 * Shows a warning dialog using {@link MessageDialog#openWarning(Shell, String, String)}. {@link RB#WARNING_GENERAL_TITLE} is used as the title
	 *
	 * @param message The message content
	 * @see MessageDialog#openWarning(Shell, String, String)
	 */
	public static void showWarning(String message)
	{
		Display.getDefault().asyncExec(() -> MessageDialog.openWarning(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.WARNING_GENERAL_TITLE), message));
	}

	/**
	 * Shows a question dialog using {@link MessageDialog#openQuestion(Shell, String, String)}. {@link RB#CONFIRM_GENERAL_TITLE} is used as the title
	 *
	 * @param message       The message content
	 * @param toggleMessage The toggle state message
	 * @param checked       The toggle state of the remember button
	 * @see MessageDialog#openQuestion(Shell, String, String)
	 */
	public static Tuple.Pair<Boolean, Boolean> showQuestionToggle(String message, String toggleMessage, boolean checked)
	{
		final Tuple.Pair<Boolean, Boolean> result = new Tuple.Pair<>();

		Display.getDefault().syncExec(() -> {
			MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.ERROR_GENERAL_TITLE), message, toggleMessage,
					checked, null, null);

			boolean decision = dialog.getReturnCode() == IDialogConstants.YES_ID;
			boolean toggleResult = dialog.getToggleState();

			result.setFirst(decision);
			result.setSecond(toggleResult);
		});

		return result;
	}

	/**
	 * Shows a question dialog using {@link MessageDialog#openQuestion(Shell, String, String)}. {@link RB#CONFIRM_GENERAL_TITLE} is used as the title
	 *
	 * @param message  The message content
	 * @param callback The {@link DialogCallback} called with the result of {@link MessageDialog#openQuestion(Shell, String, String)}
	 * @see MessageDialog#openQuestion(Shell, String, String)
	 */
	public static void showQuestion(String message, DialogCallback callback)
	{
		Display.getDefault().asyncExec(() -> {
			boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), RB.getStringInternal(RB.CONFIRM_GENERAL_TITLE), message);

			if (callback != null)
				callback.onResult(result);
		});
	}

	/**
	 * Handles the {@link Exception} based on its type.
	 * <p>
	 * The fallback solution is to simply call {@link #showError(String)} with {@link Exception#getLocalizedMessage()}. Depending on the type of
	 * {@link Exception}, the behaviour may differ.
	 * <p>
	 * In any case, {@link Exception#printStackTrace()} is called just before the method returns
	 *
	 * @param e The {@link Exception}
	 */
	public static void handleException(Exception e)
	{
		showError(e.getLocalizedMessage());
		e.printStackTrace();
	}

	/**
	 * {@link DialogCallback} is used in combination with {@link DialogUtils#showQuestion(String, DialogCallback)}.
	 *
	 * @author Sebastian Raubach
	 */
	public interface DialogCallback
	{
		/**
		 * This method will be called with the result of {@link MessageDialog#openQuestion(Shell, String, String)}.
		 *
		 * @param result The result of {@link MessageDialog#openQuestion(Shell, String, String)}
		 */
		void onResult(boolean result);
	}
}
