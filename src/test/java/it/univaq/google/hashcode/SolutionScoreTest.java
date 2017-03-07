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
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.util.IOUtil;
import it.univaq.google.hashcode.util.ProblemUtil;

public class SolutionScoreTest {
	@Test
	public void test_kittens() {
		try {
			testScore("kittens", 265332L);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_me_at_the_zoo() {
		try {
			testScore("me_at_the_zoo", 453049L);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_trending_today() {
		try {
			testScore("trending_today", 499980L);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_videos_worth_spreading() {
		try {
			testScore("videos_worth_spreading", 470374L);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void testScore(String fileName, long score) throws IOException {
		ProblemInstance problemInstance = IOUtil
				.parseInput(new File(Property.INPUT_TEST_RESOURCES + fileName + Property.INPUT_FILE_EXTENSION));
		
		List<String> inputLines = FileUtils.readLines(
				new File(Property.OUTPUT_TEST_RESOURCES + fileName + Property.OUTPUT_FILE_EXTENSION), Charsets.ISO_8859_1);

		String[] first_row = inputLines.get(0).split(" ");
		for (int i = 1; i <= Integer.parseInt(first_row[0]); i++) {
			String[] cache_row = inputLines.get(i).split(" ");
			for (int j = 1; j < cache_row.length; j++) {
				problemInstance.getCacheServers()[Integer.parseInt(cache_row[0])].getVideos()
						.add(problemInstance.getVideos()[Integer.parseInt(cache_row[j])]);
			}
		}
		
		Assert.assertEquals(score, ProblemUtil.calculateScore(problemInstance));
	}
}
