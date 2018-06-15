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

import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class SystemUtils
{
	/**
	 * Generates and returns a globally unique identifier of the given length. The ID will be made of the digits 0-9 and the letters A-F.
	 *
	 * @param size the length of the ID to generate
	 * @return a globally unique identifier of the given length
	 */
	public static String createGUID(int size)
	{
		String id = "";

		Random rnd = new Random();
		for (int i = 0; i < size; i++)
		{
			int code = rnd.nextInt(16);

			// Use digits 0 to 9 to generate a number
			if (code < 10)
				id += code;
				// Otherwise, generate an ASCII 65+ A to F letter
			else
				id += (char) (65 + (code - 10));
		}

		return id;
	}
}
