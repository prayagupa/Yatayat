/**
 * 
 */
package com.yatayat.android.models;

/**
 * @author prayag
 * 
 */
public class Stop {
	private long id;
	private String name;
	private double lat;
	private double lng;

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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
