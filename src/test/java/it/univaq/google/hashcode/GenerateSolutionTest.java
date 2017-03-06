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
import java.io.IOException;

import org.junit.Test;

import it.univaq.google.hashcode.impl.GreedySolvableImpl;
import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.model.Solution;
import it.univaq.google.hashcode.util.IOUtil;
import it.univaq.google.hashcode.util.ProblemUtil;

public class GenerateSolutionTest {

	@Test
	public void test_kittens() {
		try {
			generateSolution("kittens");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_me_at_the_zoo() {
		try {
			generateSolution("me_at_the_zoo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_trending_today() {
		try {
			generateSolution("trending_today");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_videos_worth_spreading() {
		try {
			generateSolution("videos_worth_spreading");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateSolution(String fileName) throws IOException {
		System.out.println("RUNNING: " + fileName);
		long start = System.currentTimeMillis();
		ProblemInstance problemInstance = IOUtil
				.parseInput(new File(Property.INPUT_TEST_RESOURCES + fileName + Property.INPUT_FILE_EXTENSION));

		ISolvable solvable = new GreedySolvableImpl();

		Solution solution = solvable.getSolution(problemInstance);
		IOUtil.generateOutput(solution, new File(Property.OUTPUT_GENERATED_SOLUTION + fileName + "_"
				+ solution.getScore() + "_" + ProblemUtil.getActualTime(".") + Property.OUTPUT_FILE_EXTENSION));
		System.out.println("END RUNNING: " + fileName + "  -  " + (System.currentTimeMillis() - start) + " ms");
	}

}
