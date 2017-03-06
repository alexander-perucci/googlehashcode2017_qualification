/*
 * googlehashcode2017_qualification - Copyright (C) 2017 iGoogle team's
 *
 * googlehashcode2017_qualification is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * googlehashcode2017_qualification is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *   
 * You should have received a copy of the GNU General Public License
 * along with googlehashcode2017_qualification.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.univaq.google.hashcode;

import java.io.File;

public class Property {
	public static final String TEST_RESOURCES = "." + File.separatorChar + "src" + File.separatorChar + "test" + File.separatorChar + "resources" + File.separatorChar;
	public static final String INPUT_TEST_RESOURCES = TEST_RESOURCES + "input" + File.separatorChar;
	public static final String OUTPUT_TEST_RESOURCES= TEST_RESOURCES + "output" + File.separatorChar;
	public static final String OUTPUT_GENERATED_SOLUTION = "." + File.separatorChar + "generated_solution" + File.separatorChar;
	public static final String INPUT_FILE_EXTENSION = ".in";
	public static final String OUTPUT_FILE_EXTENSION = ".out";
}
