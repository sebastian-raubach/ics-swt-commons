/*
 * JHI-SWT-Commons is written and developed by Sebastian Raubach
 * from the Information and Computational Sciences Group at JHI Dundee.
 * For further information contact us at sebastian.raubach@hutton.ac.uk.
 *
 * Copyright © 2014-2015, Information & Computational Sciences,
 * The James Hutton Institute. All rights reserved.
 * Use is subject to the accompanying licence terms.
 */

package org.jhi.desktop.commons.util;

/**
 * Valid {@link Parameter}s of the {@link ParameterStore}
 *
 * @author Sebastian Raubach
 *
 */
public interface Parameter
{
	Class<?> getType();
}