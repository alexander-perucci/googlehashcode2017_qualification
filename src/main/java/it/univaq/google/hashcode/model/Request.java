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

public class Request {
	private Endpoint endpoint;
	private Video video;
	private int numberOfRequest;

	public Request(Endpoint endpoint, Video video, int numberOfRequest) {
		this.endpoint = endpoint;
		this.video = video;
		this.numberOfRequest = numberOfRequest;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public Video getVideo() {
		return video;
	}

	public int getNumberOfRequest() {
		return numberOfRequest;
	}
}
