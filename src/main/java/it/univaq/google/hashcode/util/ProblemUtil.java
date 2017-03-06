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
import java.util.Date;

import it.univaq.google.hashcode.model.ProblemInstance;

public class ProblemUtil {

	public static String getActualTime() {
		return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
	}

	public static String getActualTime(String separator) {
		return new SimpleDateFormat("HH" + separator + "mm" + separator + "ss.SSS").format(new Date());
	}

	public static long calculateScore(ProblemInstance problemInstance) {
		/*List<Endpoint> endpoints = problemInstance.getEndpoints();

		long totalRequests = 0;
		long savedTime = 0;
		
		for (Endpoint endpoint : endpoints) {
			for (Map.Entry<Video, Integer> entry_request : endpoint.getRequests().entrySet()) {
				totalRequests+=entry_request.getValue();
				Video requestedVideo = entry_request.getKey();
				long minTime = endpoint.getLatencyToDatacenter();
				for (Map.Entry<CacheServer, Integer> entry_cache : endpoint.getCaches().entrySet()) {
					if(entry_cache.getKey().getVideos().contains(requestedVideo)){
						if (entry_cache.getValue()< minTime){
							minTime = entry_cache.getValue();
						}
					}
				}			
				savedTime+= entry_request.getValue() * (endpoint.getLatencyToDatacenter()-minTime);
			}
		}
		
		return (savedTime*1000)/totalRequests;
		*/
		return 0L;
	}
	
	
}
