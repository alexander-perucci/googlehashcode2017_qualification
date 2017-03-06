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
package it.univaq.google.hashcode.model;

public class ProblemInstance {
	private int chacheSize;
	private Video[] videos;
	private Endpoint[] endpoints;
	private CacheServer[] cacheServers;
	private Request[] requests;

	public ProblemInstance(int nVideo, int nEndpoint, int nCacheServer, int nRequest, int chacheSize) {
		this.videos = new Video[nVideo];
		this.endpoints = new Endpoint[nEndpoint];
		this.cacheServers = new CacheServer[nCacheServer];
		this.requests = new Request[nRequest];
		this.chacheSize = chacheSize;

		for (int i = 0; i < nCacheServer; i++) {
			CacheServer cacheServer = new CacheServer();
			cacheServer.setId(i);
			cacheServer.setSize(chacheSize);
			cacheServers[i] = cacheServer;
		}
	}

	public int getChacheSize() {
		return chacheSize;
	}

	public Video[] getVideos() {
		return videos;
	}

	public Endpoint[] getEndpoints() {
		return endpoints;
	}

	public CacheServer[] getCacheServers() {
		return cacheServers;
	}

	public Request[] getRequests() {
		return requests;
	}

}
