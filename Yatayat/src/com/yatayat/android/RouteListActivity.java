/**
 * 
 */
package com.yatayat.android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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

		routeList = new ArrayList<Route>();
		routeArrayAdapter = new RouteArrayAdapter(this, routeList);
		routeListView.setAdapter(routeArrayAdapter);

		Thread thread = new Thread(null, loadRoutes);
		thread.start();
	}

	private final Runnable loadRoutes = new Runnable() {

		@Override
		public void run() {
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
				for (Route r : routeList) {
					routeArrayAdapter.add(r);
				}
			}
			routeArrayAdapter.notifyDataSetChanged();

			progressLinearLayout.setVisibility(View.GONE);
			routeListView.setVisibility(View.VISIBLE);
		}
	};
}
