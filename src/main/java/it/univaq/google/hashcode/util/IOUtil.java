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
package it.univaq.google.hashcode.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import it.univaq.google.hashcode.model.CacheServer;
import it.univaq.google.hashcode.model.Endpoint;
import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.model.Request;
import it.univaq.google.hashcode.model.Solution;
import it.univaq.google.hashcode.model.Video;

public class IOUtil {

	private static final String SEPARATOR = " ";

	public static ProblemInstance parseInput(File inputFile) throws IOException {
		List<String> inputLines = FileUtils.readLines(inputFile, Charsets.ISO_8859_1);


		String[] first_row = inputLines.get(0).split(SEPARATOR);
		int nVideo = Integer.parseInt(first_row[0]);
		int nEndpoint = Integer.parseInt(first_row[1]);
		int nRequest = Integer.parseInt(first_row[2]);
		int nCacheServer = Integer.parseInt(first_row[3]);
		int chacheSize = Integer.parseInt(first_row[4]);

		ProblemInstance problemInstance = new ProblemInstance(nVideo, nEndpoint, nCacheServer, nRequest, chacheSize);

		String[] second_row = inputLines.get(1).split(SEPARATOR);
		for (int i = 0; i < second_row.length; i++) {
			problemInstance.getVideos()[i] = new Video(i, Integer.parseInt(second_row[i]));
		}

		int actualRow = 2;
		for (int i = 0; i < problemInstance.getEndpoints().length; i++) {
			String[] endpoint_row = inputLines.get(actualRow).split(SEPARATOR);
			actualRow += 1;
			Endpoint endpoint = new Endpoint(Integer.parseInt(endpoint_row[0]));
			for (int j = 0; j < Integer.parseInt(endpoint_row[1]); j++) {
				String[] cache_row = inputLines.get(actualRow).split(SEPARATOR);
				actualRow += 1;
				endpoint.getLatencyToCacheServer().put(
						problemInstance.getCacheServers()[Integer.parseInt(cache_row[0])],
						Integer.parseInt(cache_row[1]));
			}
			problemInstance.getEndpoints()[i] = endpoint;
		}

		for (int i = 0; i < problemInstance.getRequests().length; i++) {
			String[] request_row = inputLines.get(actualRow).split(SEPARATOR);
			actualRow += 1;
			problemInstance.getRequests()[i] = new Request(
					problemInstance.getEndpoints()[Integer.parseInt(request_row[1])],
					problemInstance.getVideos()[Integer.parseInt(request_row[0])], Integer.parseInt(request_row[2]));
		}

		return problemInstance;

	}

	public static void generateOutput(Solution solution, File outFile) throws IOException {
		System.out.println("- SOLUTION Score: " + solution.getScore());
		StringBuilder solutionString = new StringBuilder();
		solutionString.append(solution.getCacheServers().size() + "\n");

		for (CacheServer cacheServer : solution.getCacheServers()) {
			solutionString.append(cacheServer.getId());
			for (Video video : cacheServer.getVideos()) {
				solutionString.append(SEPARATOR + video.getId());

			}
			solutionString.append("\n");
		}

		FileUtils.writeStringToFile(outFile, solutionString.toString(), Charsets.ISO_8859_1);
	}

}
