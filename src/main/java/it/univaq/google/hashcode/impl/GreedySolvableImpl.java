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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.google.hashcode.ISolvable;
import it.univaq.google.hashcode.model.CacheServer;
import it.univaq.google.hashcode.model.Endpoint;
import it.univaq.google.hashcode.model.ProblemInstance;
import it.univaq.google.hashcode.model.Request;
import it.univaq.google.hashcode.model.Solution;
import it.univaq.google.hashcode.model.Video;

public class GreedySolvableImpl implements ISolvable {

	@Override
	public Solution getSolution(ProblemInstance problemInstance) {
		List<Video> consideredVideos = new ArrayList<Video>();
		List<CacheServer> cacheServers = new ArrayList<CacheServer>();

		boolean continueToCreateSolution = true;

		while (continueToCreateSolution) {
			Video popularVideo = getPopularVideo(problemInstance, consideredVideos);

			if (popularVideo == null) {
				continueToCreateSolution = false;
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

		return new Solution(0L, cacheServers);
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
		List<Endpoint> endpoints = new ArrayList<Endpoint>();

		for (int i = 0; i < problemInstance.getRequests().length; i++) {
			if (problemInstance.getRequests()[i].getVideo().equals(video)) {
				endpoints.add(problemInstance.getRequests()[i].getEndpoint());
			}
		}

		return endpoints;
	}

	private List<CacheServer> getGreedyCacheServers(List<Endpoint> endpoints, Video video) {
		List<CacheServer> cacheServers = new ArrayList<CacheServer>();
		boolean iterate = true;

		List<Endpoint> newEndpoints = new ArrayList<Endpoint>(endpoints);

		while (iterate) {
			CacheServer cacheServer = getGreedyCacheServer(newEndpoints, video);
			if (cacheServer == null) {
				return cacheServers;
			}

			List<Endpoint> toberemoved = new ArrayList<Endpoint>();

			for (Endpoint endpoint : newEndpoints) {
				if (endpoint.getLatencyToCacheServer().keySet().contains(cacheServer)) {
					toberemoved.add(endpoint);
				}
			}
			newEndpoints.removeAll(toberemoved);
			cacheServers.add(cacheServer);
			if (newEndpoints.isEmpty()) {
				return cacheServers;
			}

		}

		return cacheServers;
	}

	private CacheServer getGreedyCacheServer(List<Endpoint> endpoints, Video video) {
		Map<CacheServer, Integer> sharedCacheServer = new HashMap<CacheServer, Integer>();
		for (Endpoint endpoint : endpoints) {
			for (CacheServer cacheServer : endpoint.getLatencyToCacheServer().keySet()) {
				if (sharedCacheServer.containsKey(cacheServer)) {
					sharedCacheServer.put(cacheServer, sharedCacheServer.get(cacheServer) + 1);
				} else {
					sharedCacheServer.put(cacheServer, 1);
				}
			}
		}

		Map<CacheServer, Integer> odered = new HashMap<CacheServer, Integer>();
		sharedCacheServer.entrySet().stream().sorted(Map.Entry.<CacheServer, Integer>comparingByValue().reversed())
				.forEachOrdered(x -> odered.put(x.getKey(), x.getValue()));

		for (CacheServer cacheServer : odered.keySet()) {
			if (cacheServer.getUsedSpace() + video.getSize() <= cacheServer.getSize()) {
				return cacheServer;
			}
		}

		return null;
	}
}
