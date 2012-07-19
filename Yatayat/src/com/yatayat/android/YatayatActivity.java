package com.yatayat.android;

/**
 * @author prayag
 * @created_date   15th July 2012 
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;

import com.yatayat.android.models.Stop;
import com.yatayat.android.service.YatayatService;
import com.yatayat.android.utils.YatayatUtility;
import com.yatayat.android.widgets.StopArrayAdaptor;

public class YatayatActivity extends Activity {

	private AutoCompleteTextView startPoint, endPoint;
	private ScrollView mainSV, noInternetSV, progressSV;
	private List<Stop> stopsList;
	private StopArrayAdaptor stopArrayAdaptor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yatayat);

		stopsList = new ArrayList<Stop>();
		// if YES connection
		if (YatayatUtility.checkInternetConnection(this)) {
			progressSV = (ScrollView) findViewById(R.id.loading_sv);
			progressSV.setVisibility(View.VISIBLE);

			mainSV = (ScrollView) findViewById(R.id.main_sv);
			mainSV.setVisibility(View.GONE);

			startPoint = (AutoCompleteTextView) findViewById(R.id.start_point_et);
			endPoint = (AutoCompleteTextView) findViewById(R.id.end_point_et);
			stopArrayAdaptor = new StopArrayAdaptor(this, stopsList);

			startPoint.setAdapter(stopArrayAdaptor);
			endPoint.setAdapter(stopArrayAdaptor);

			Thread thread = new Thread(null, loadStops);
			thread.start();
		} else {
			mainSV = (ScrollView) findViewById(R.id.no_internet_sv);
			mainSV.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_yatayat, menu);
		return true;
	}

	private final Runnable loadStops = new Runnable() {

		public void run() {
			YatayatService.init();
			stopsList = YatayatService.getStops();
			runOnUiThread(returnResponse);
		}
	};
	private final Runnable returnResponse = new Runnable() {

		public void run() {
			if (stopsList != null && stopsList.size() > 0) {
				for (int i = 0; i < stopsList.size(); i++) {
					stopArrayAdaptor.add(stopsList.get(i));
				}
			}

			if (progressSV.getVisibility() == View.VISIBLE) {
				progressSV.setVisibility(View.GONE);
				mainSV.setVisibility(View.VISIBLE);
			}
			stopArrayAdaptor.notifyDataSetChanged();
			// Log.i("NOTIFIED_TO_ADAPTER", "ADDING_TO_ADAPTER");

		}
	};

}
