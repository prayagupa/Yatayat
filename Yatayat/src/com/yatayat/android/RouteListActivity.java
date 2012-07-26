/**
 * 
 */
package com.yatayat.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yatayat.android.models.Route;
import com.yatayat.android.service.YatayatService;
import com.yatayat.android.widgets.RouteArrayAdapter;

/**
 * @author prayag
 * @created 21 Jul 2012
 * @filename RoutesDetailActivity.java
 */
public class RouteListActivity extends Activity {
	private ListView routeListView;
	private RouteArrayAdapter routeArrayAdapter;
	private LinearLayout progressLinearLayout;

	private long startStopID;
	private long goalStopID;
	double startLat;
	double startLng;

	double goalLat;
	double goalLng;
	ArrayList<Route> routeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routelist);

		routeListView = (ListView) findViewById(R.id.activity_routelist_lv);
		routeListView.setVisibility(View.INVISIBLE);

		progressLinearLayout = (LinearLayout) findViewById(R.id.activity_routelist_loading_ll);
		progressLinearLayout.setVisibility(View.VISIBLE);

		Bundle bundle = getIntent().getExtras();
		startStopID = bundle.getLong("startStopID");
		goalStopID = bundle.getLong("goalStopID");

		startLat = Double.valueOf(bundle.getString("startLat"));
		startLng = bundle.getDouble("startLng");

		goalLat = bundle.getDouble("goalLat");
		goalLng = bundle.getDouble("goalLng");

		routeList = new ArrayList<Route>();
		routeArrayAdapter = new RouteArrayAdapter(this, routeList);
		routeListView.setAdapter(routeArrayAdapter);

		routeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("position", "" + position);
				Log.i("id", "" + id);
				Route selectedRoute = routeList.get(position);
				Log.i("ROUTE_NAME", selectedRoute.getName());
				// ArrayList<Stop> stops = selectedRoute.getStops();
				// Log.i("SIZE_OF_STOPS", stops.size() + "");
				// Stop stop = stops.get(0);
				// double d = stop.getLat();
				// Log.i("___", d + "");
				//
				Intent intent = new Intent();
				// intent.putExtra("startLat", String.valueOf(selectedRoute
				// .getStops().get(0).getLat()));
				//
				// intent.putExtra("startLng", String.valueOf(selectedRoute
				// .getStops().get(0).getLng()));
				//
				// intent.putExtra(
				// "goalLat",
				// String.valueOf(selectedRoute.getStops()
				// .get(selectedRoute.getStops().size() - 1)
				// .getLat()));
				// intent.putExtra(
				// "goalLng",
				// String.valueOf(selectedRoute.getStops()
				// .get(selectedRoute.getStops().size() - 1)
				// .getLng()));
				//
				// intent.putExtra("routeName",
				// String.valueOf(selectedRoute.getName()));
				intent.putExtra("startLat", String.valueOf(startLat));
				Log.i("LAT_ROUTELIST_ACTIVITY", String.valueOf(startLat));
				intent.putExtra("startLng", String.valueOf(startLng));

				intent.putExtra("goalLat", String.valueOf(goalLat));

				intent.putExtra("goalLng", String.valueOf(goalLng));

				intent.setClass(RouteListActivity.this,
						YatayatMapActivity.class);
				startActivity(intent);

			}

		});
		Thread thread = new Thread(null, loadRoutes);
		thread.start();
	}

	private final Runnable loadRoutes = new Runnable() {

		@Override
		public void run() {
			Log.i("START_STOP_ID", "" + startStopID);
			Log.i("GOAL_STOP_ID", "" + goalStopID);
			routeList = YatayatService.takeMeThere(startStopID, goalStopID);
			if (routeList == null) {

			}
			runOnUiThread(response);
		}
	};
	private final Runnable response = new Runnable() {

		@Override
		public void run() {
			if (routeList.size() > 0 && routeList != null) {
				for (Route routes : routeList) {
					routeArrayAdapter.add(routes);
				}
			}
			routeArrayAdapter.notifyDataSetChanged();

			progressLinearLayout.setVisibility(View.GONE);
			routeListView.setVisibility(View.VISIBLE);
		}
	};
}
