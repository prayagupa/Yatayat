/**
 * 
 */
package com.yatayat.android.models;

import java.util.ArrayList;

/**
 * @author prayag
 * @created 15 Jul 2012
 * @filename Route.java
 */
public class Route {
	private long id;
	private String name;
	private Vehicle vehicle;
	private ArrayList<Stop> stops;

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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ArrayList<Stop> getStops() {
		return stops;
	}

	public void setStops(ArrayList<Stop> stops) {
		this.stops = stops;
	}

}
