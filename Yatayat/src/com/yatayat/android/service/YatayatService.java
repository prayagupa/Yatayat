/**
 * 
 */
package com.yatayat.android.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yatayat.android.models.Route;
import com.yatayat.android.models.Stop;
import com.yatayat.android.utils.JsonParser;
import com.yatayat.android.utils.YatayatConstants;

/**
 * @author prayag
 * @created 15 Jul 2012
 * @filename YatayatService.java
 */
public class YatayatService {
	private static List<Route> routes = null;
	private static List<Stop> stops = null;

	public static List<Route> getAllRoute() {
		return routes;
	}

	public static void setRoute(List<Route> allRoute) {
		YatayatService.routes = allRoute;
	}

	public static List<Stop> getStops() {
		return stops;
	}

	public static void setStops(List<Stop> allStops) {
		YatayatService.stops = allStops;
	}

	public static void init() {

		String jsonString = JsonParser
				.parseJSON(YatayatConstants.ROUTES_LIST_URL);

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
		// return routes;

	}

	public static void getRouteNames() {
		String response = JsonParser
				.parseJSON(YatayatConstants.ROUTES_LIST_URL);
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
	}
	/* end of getRoutes */
}
