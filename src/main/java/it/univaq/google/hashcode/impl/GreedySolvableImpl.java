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
package it.univaq.google.hashcode.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.univaq.google.hashcode.ISolvable;
import it.univaq.google.hashcode.model.CacheServer;
import it.univaq.google.hashcode.model.Endpoint;
import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.model.Request;
import it.univaq.google.hashcode.model.Solution;
import it.univaq.google.hashcode.model.Video;
import it.univaq.google.hashcode.util.ProblemUtil;

public class GreedySolvableImpl implements ISolvable {

	@Override
	public Solution getSolution(ProblemInstance problemInstance) {
		List<Video> consideredVideos = new LinkedList<Video>();
		List<CacheServer> cacheServers = new LinkedList<CacheServer>();

		while (true) {
			Video popularVideo = getPopularVideo(problemInstance, consideredVideos);
			if (popularVideo == null) {
				return new Solution(ProblemUtil.calculateScore(problemInstance), cacheServers);
			}

			List<Endpoint> endpoints = getEndpoints(problemInstance, popularVideo);
			for (CacheServer cacheServer : getGreedyCacheServers(endpoints, popularVideo)) {
				cacheServer.getVideos().add(popularVideo);
				cacheServer.setUsedSpace(cacheServer.getUsedSpace() + popularVideo.getSize());

				if (!cacheServers.contains(cacheServer)) {
					cacheServers.add(cacheServer);
				}
			}
			consideredVideos.add(popularVideo);
		}

	}

	private Video getPopularVideo(ProblemInstance problemInstance, List<Video> videosToBeExcluded) {
		Request selectedRequest = null;

		for (int i = 0; i < problemInstance.getRequests().length; i++) {
			Request request = problemInstance.getRequests()[i];
			if (!videosToBeExcluded.contains(request.getVideo())) {
				if (selectedRequest == null) {
					selectedRequest = request;
				} else {
					if (selectedRequest.getNumberOfRequest() < request.getNumberOfRequest()) {
						selectedRequest = request;
					}
				}
			}
		}

		if (selectedRequest == null) {
			return null;
		}

		return selectedRequest.getVideo();
	}

	private List<Endpoint> getEndpoints(ProblemInstance problemInstance, Video video) {
		List<Endpoint> endpoints = new LinkedList<Endpoint>();

		for (int i = 0; i < problemInstance.getRequests().length; i++) {
			if (problemInstance.getRequests()[i].getVideo().equals(video)) {
				endpoints.add(problemInstance.getRequests()[i].getEndpoint());
			}
		}
		return endpoints;
	}

	private List<CacheServer> getGreedyCacheServers(List<Endpoint> endpoints, Video video) {
		List<CacheServer> cacheServers = new LinkedList<CacheServer>();
		List<Endpoint> newEndpoints = new LinkedList<Endpoint>(endpoints);

		while (true) {
			if (newEndpoints.isEmpty()) {
				return cacheServers;
			}

			CacheServer cacheServer = getGreedyCacheServer(newEndpoints, video);
			if (cacheServer == null) {
				return cacheServers;
			}

			List<Endpoint> toberemoved = new LinkedList<Endpoint>();

			for (Endpoint endpoint : newEndpoints) {
				if (endpoint.getLatencyToCacheServer().keySet().contains(cacheServer)) {
					toberemoved.add(endpoint);
				}
			}
			newEndpoints.removeAll(toberemoved);
			cacheServers.add(cacheServer);
		}
	}

	private CacheServer getGreedyCacheServer(List<Endpoint> endpoints, Video video) {
		Map<CacheServer, Integer> sharedCacheServer = new LinkedHashMap<CacheServer, Integer>();
		Map<CacheServer, Integer> latencyCacheServer = new LinkedHashMap<CacheServer, Integer>();
		for (Endpoint endpoint : endpoints) {
			for (Map.Entry<CacheServer, Integer> mapCacheServerLatency : endpoint.getLatencyToCacheServer()
					.entrySet()) {
				if (sharedCacheServer.containsKey(mapCacheServerLatency.getKey())) {
					sharedCacheServer.put(mapCacheServerLatency.getKey(),
							sharedCacheServer.get(mapCacheServerLatency.getKey()) + 1);
					latencyCacheServer.put(mapCacheServerLatency.getKey(),
							latencyCacheServer.get(mapCacheServerLatency.getKey()) + mapCacheServerLatency.getValue());
				} else {
					sharedCacheServer.put(mapCacheServerLatency.getKey(), mapCacheServerLatency.getValue());
					latencyCacheServer.put(mapCacheServerLatency.getKey(), mapCacheServerLatency.getValue());

				}
			}
		}

		Map<CacheServer, Integer> ordered = new LinkedHashMap<CacheServer, Integer>();
		
		 /*  sharedCacheServer.entrySet().stream().sorted(Map.Entry.<CacheServer, Integer>comparingByValue().reversed())
				.forEachOrdered(x -> ordered.put(x.getKey(), x.getValue()));
	     */
		ordered = ProblemUtil.reverseSortByValue(sharedCacheServer, latencyCacheServer);
	
		for (CacheServer cacheServer : ordered.keySet()) {
			if (cacheServer.getUsedSpace() + video.getSize() <= cacheServer.getSize()) {
				return cacheServer;
			}
		}
		

		return null;
	}

	
}
