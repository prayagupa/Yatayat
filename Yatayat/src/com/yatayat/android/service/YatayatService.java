/**
 * 
 */
package com.yatayat.android.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yatayat.android.models.Route;
import com.yatayat.android.models.Stop;
import com.yatayat.android.utils.JsonParser;
import com.yatayat.android.utils.YatayatConstants;

/**
 * @author prayag
 * @created 15 Jul 2012
 * @lastmodified 20 Jul 2012
 * @filename YatayatService.java
 */
public class YatayatService {
	static String TAG_YATAYAT_SERVICE = "com.yatayat.android.service.YatayatService";
	static String YATAYAT_SERVICE = "YatayatService";
	private static List<Route> routes = null;
	private static List<Stop> stops = null;
	private static Set<Stop> stopsSet;
	private static ArrayList<Stop> uniqueStops;

	public static List<Route> getAllRoute() {
		return routes;
	}

	public static void setRoute(List<Route> allRoute) {
		YatayatService.routes = allRoute;
	}

	public static List<Stop> getStops() {
		return uniqueStops;
	}

	public static void setStops(List<Stop> allStops) {
		YatayatService.stops = allStops;
	}

	public static void init() {
		stops = new ArrayList<Stop>();
		routes = new ArrayList<Route>();
		String jsonString = JsonParser
				.parseJSON(YatayatConstants.STOPS_LIST_URL);

		try {

			JSONObject parentObject = new JSONObject(jsonString);

			String json = parentObject.getString("routes");
			JSONArray routesArray = new JSONArray(json);

			for (int i = 0; i < routesArray.length(); i++) {
				JSONObject routeObj = routesArray.getJSONObject(i);

				Route route = new Route();
				route.setId(Long.parseLong(routeObj.getString("id")));
				route.setName(routeObj.getString("name"));
				route.setRef(routeObj.getString("ref"));
				route.setTransport(routeObj.getString("transport"));
				String stopsJson = routeObj.getString("stops");
				// Log.i(YATAYAT_SERVICE, stopsJson);
				JSONArray stopsArray = new JSONArray(stopsJson);
				if (stopsArray.length() > 0) {
					// Log.i("LENGTH_OF_STOPS",
					// String.valueOf(stopsArray.length()));
					for (int j = 0; j < stopsArray.length(); j++) {
						// Log.i("COUNTER" + j, j + "");
						Stop stop = new Stop();
						JSONObject stopObject = stopsArray.getJSONObject(j);
						if (stopObject.has("id")) {
							// Log.i("ID", (stopObject.getString("id")));
							stop.setId(Long.parseLong(stopObject
									.getString("id")));
						}
						if (stopObject.has("name")) {
							Log.i("NAME", (stopObject.getString("name")));
							stop.setName(stopObject.getString("name"));
						}
						if (stopObject.has("lat")) {
							// Log.i("LAT", (stopObject.getString("lat")));
							stop.setLat(Double.parseDouble(stopObject
									.getString("lat")));
						}
						if (stopObject.has("lng")) {
							// Log.i("LNG", (stopObject.getString("lng")));
							stop.setLng(Double.parseDouble(stopObject
									.getString("lng")));
						}
						// Log.i("ADDING", "ADDING");
						stops.add(stop);
						// Log.i("ADDED", "ADDED");
					}
				}
				/* end of stops loop */
				routes.add(route);
			}
			/* end of routes loop */
		} catch (JSONException e) {
			e.printStackTrace();
		}
		stopsSet = new HashSet(stops);
		uniqueStops = new ArrayList(stopsSet);
		// return routes;

	}

	public static ArrayList<Stop> getAllStops() {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		String yatayatResponse = JsonParser
				.parseJSON(YatayatConstants.STOPS_LIST_URL);
		try {

			JSONArray stopsArray = new JSONArray(yatayatResponse);
			if (stopsArray.length() > 0) {
				for (int j = 0; j < stopsArray.length(); j++) {
					Stop stop = new Stop();
					JSONObject stopObject = stopsArray.getJSONObject(j);
					if (stopObject.has("id")) {
						stop.setId(Long.parseLong(stopObject.getString("id")));
					}
					if (stopObject.has("name")) {
						Log.i("NAME", (stopObject.getString("name")));
						stop.setName(stopObject.getString("name"));
					}
					if (stopObject.has("lat")) {
						stop.setLat(Double.parseDouble(stopObject
								.getString("lat")));
					}
					if (stopObject.has("lng")) {
						stop.setLng(Double.parseDouble(stopObject
								.getString("lng")));
					}

					stops.add(stop);
				}
			}
			/* end of stops loop */
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return stops;
	}

	public static List<Route> getAllRoutes(String jsonString_) {
		String jsonString = JsonParser
				.parseJSON(YatayatConstants.ROUTES_LIST_URL);

		List<Route> routes = null;
		List<Stop> stops = null;
		try {

			JSONObject parentObject = new JSONObject(jsonString);

			String json = parentObject.getString("routes");
			JSONArray routesArray = new JSONArray(json);

			for (int i = 0; i < routesArray.length(); i++) {
				JSONObject routeObj = routesArray.getJSONObject(i);
				/**
				 * 
				 */
				Route route = new Route();
				route.setId(Long.parseLong(routeObj.getString("id")));
				route.setName(routeObj.getString("name"));
				route.setRef(routeObj.getString("ref"));
				route.setTransport(routeObj.getString("transport"));
				String stopsJson = routeObj.getString("stops");
				// System.out.println(stopsJson);
				JSONArray stopsArray = new JSONArray(stopsJson);
				if (stopsArray.length() > 0) {
					for (int j = 0; j < stopsArray.length(); j++) {
						Stop stop = new Stop();
						JSONObject stopObject = stopsArray.getJSONObject(j);
						if (stopObject.has("id")) {
							stop.setId(Long.parseLong(stopObject
									.getString("id")));
						}
						if (stopObject.has("name")) {
							stop.setName(stopObject.getString("name"));
						}
						if (stopObject.has("lat")) {
							stop.setLat(Double.parseDouble(stopObject
									.getString("name")));
						}
						if (stopObject.has("lng")) {
							stop.setLng(Double.parseDouble(stopObject
									.getString("lng")));
						}

						stops.add(stop);
					}
				}
				/* end of stops loop */
				routes.add(route);
			}
			/* end of routes loop */
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routes;
		/* end of getRoutes */
	}

	/**
	 * 
	 * @param lat
	 * @param lng
	 * @return
	 * @description nearest stops to lat/lng position
	 */

	public ArrayList<Stop> getNearestStops(double lat, double lng) {
		String GET_NEAREST_STOPS = "GET_NEAREST_STOPS";
		ArrayList<Stop> nearestStops = new ArrayList<Stop>();
		String yatayatRequest = YatayatConstants.NEAREST_STOPS_URL + "?lat="
				+ lat + "&lng=" + lng;
		String yatayatResponse = JsonParser.parseJSON(yatayatRequest);
		// TODO
		// nearestStops = convertToStops(yatayatResponse);
		return nearestStops;
	}

	/**
	 * 
	 * @param startStopID
	 * @param goalStopID
	 * @return
	 * @description :return a list of partial routes to take when going from
	 *              start to goal
	 */
	public ArrayList<Route> takeMeThere(long startStopID, long goalStopID) {
		String TAKE_ME_THERE = "TAKE_ME_THERE";
		ArrayList<Route> routes = new ArrayList<Route>();
		String takeMeThere = YatayatConstants.TAKE_ME_THERE_URL
				+ "?startStopID=" + startStopID + "&goalStopID=" + goalStopID;
		String yatayatResponse = JsonParser.parseJSON(takeMeThere);
		Log.i(TAKE_ME_THERE, yatayatResponse);
		// TODO
		// routes = convertToRoutes(yatayatResponse);

		return routes;
	}
}
