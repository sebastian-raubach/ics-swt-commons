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

package jhi.swtcommons.util;

import org.eclipse.swt.widgets.*;

@SuppressWarnings("unused")
public class WidgetUtils
{
	/**
	 * Disposes the given {@link Widget} by calling {@link Widget#dispose()} after checking that it's not <code>null</code> and that it isn't already
	 * disposed ({@link Widget#isDisposed()}).
	 *
	 * @param widget The {@link Widget} to dispose
	 */
	public static void dispose(Widget widget)
	{
		if (widget != null && !widget.isDisposed())
			widget.dispose();
	}

	/**
	 * Disposes the given {@link Widget}s by calling {@link Widget#dispose()} after checking that they aren not <code>null</code> and that they aren't
	 * already disposed ({@link Widget#isDisposed()}).
	 *
	 * @param widgets The {@link Widget}s to dispose
	 */
	public static void dispose(Widget... widgets)
	{
		if (widgets == null)
			return;

		for (Widget widget : widgets)
			dispose(widget);
	}
}
