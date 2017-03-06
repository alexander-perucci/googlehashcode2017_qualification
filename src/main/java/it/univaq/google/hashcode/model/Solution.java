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

import java.util.List;

public class Solution {
	private long score;
	private List<CacheServer> cacheServers;

	public Solution(long score, List<CacheServer> cacheServers) {
		this.score = score;
		this.cacheServers = cacheServers;
	}

	public List<CacheServer> getCacheServers() {
		return cacheServers;
	}

	public void setCacheServers(List<CacheServer> cacheServers) {
		this.cacheServers = cacheServers;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public boolean isAdmissible() {
		for (CacheServer server : cacheServers) {
			int usedSpace = 0;
			for (Video video : server.getVideos()) {
				usedSpace += video.getSize();
			}
			if (usedSpace > server.getSize())
				return false;
		}
		return true;
	}
}
