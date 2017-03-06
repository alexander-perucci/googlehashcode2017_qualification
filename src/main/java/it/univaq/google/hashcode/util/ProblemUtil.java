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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.univaq.google.hashcode.model.CacheServer;
import it.univaq.google.hashcode.model.Endpoint;
import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.model.Video;

public class ProblemUtil {

	public static String getActualTime() {
		return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
	}

	public static String getActualTime(String separator) {
		return new SimpleDateFormat("HH" + separator + "mm" + separator + "ss.SSS").format(new Date());
	}

	public static long calculateScore(ProblemInstance problemInstance) {
		double numberOfRequest = 0;
		double totalSavedTime = 0;
		for (int i = 0; i < problemInstance.getRequests().length; i++) {
			numberOfRequest += problemInstance.getRequests()[i].getNumberOfRequest();
			totalSavedTime = totalSavedTime + (problemInstance.getRequests()[i].getNumberOfRequest()
					* getSavedTime(problemInstance.getRequests()[i].getVideo(),problemInstance.getRequests()[i].getEndpoint(), problemInstance.getCacheServers()));
		}

		return (long) ((totalSavedTime / numberOfRequest)*1000);
	}

	private static int getSavedTime(Video video, Endpoint endpoint, CacheServer[] cacheServers) {
		int latencyToDatacenter = endpoint.getLatencyToDatacenter();
		int latencyToCacheServer = latencyToDatacenter;
		for (Map.Entry<CacheServer, Integer> mapCacheServerLatency : endpoint.getLatencyToCacheServer().entrySet()) {
			if (cacheServers[mapCacheServerLatency.getKey().getId()]
					.getVideos().contains(video)) {
				if (mapCacheServerLatency.getValue() < latencyToCacheServer) {
					latencyToCacheServer = mapCacheServerLatency.getValue();
				}
			}
		}
		return latencyToDatacenter - latencyToCacheServer;

	}

}
