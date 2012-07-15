/**
 * 
 */
package com.yatayat.android.models;

import java.util.List;

/**
 * @author prayag
 * @created 15 Jul 2012
 * @filename Route.java
 */
public class Route {
	private long id;
	private String name;
	private String ref;
	private String transport;
	private List<Stop> stops;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

}
