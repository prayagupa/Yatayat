package com.yatayat.android;

/**
 * @author         prayag
 * @created_date   15th July 2012
 * @lastmodified   20 Jul 2012 
 */
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView.Validator;
import android.widget.Button;
import android.widget.ScrollView;

import com.yatayat.android.models.Stop;
import com.yatayat.android.service.YatayatService;
import com.yatayat.android.utils.YatayatUtility;

public class YatayatActivity extends Activity {

	private AutoCompleteTextView startPoint, endPoint;
	private ScrollView mainSV, noInternetSV, progressSV;
	private ArrayList<String> stopsList;
	private ArrayAdapter<String> arrayAdapter;
	private String[] stopsStringArray;// = new String[999999];
	long startStopID;
	long goalStopID;
	double startLat;
	double startLng;

	double goalLat;
	double goalLng;

	private Button takeMeThere;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yatayat);

		stopsList = new ArrayList<String>();
		/**
		 * CHECK CONNECTIVITY
		 */
		if (YatayatUtility.checkInternetConnection(this)) {
			progressSV = (ScrollView) findViewById(R.id.loading_sv);
			progressSV.setVisibility(View.VISIBLE);

			mainSV = (ScrollView) findViewById(R.id.main_sv);
			mainSV.setVisibility(View.GONE);

			startPoint = (AutoCompleteTextView) findViewById(R.id.start_point_et);
			endPoint = (AutoCompleteTextView) findViewById(R.id.end_point_et);
			arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, stopsList);
			startPoint.setAdapter(arrayAdapter);
			endPoint.setAdapter(arrayAdapter);

			startPoint.setValidator(new Validator() {

				@Override
				public boolean isValid(CharSequence text) {
					Log.v("IS_VALID", "CHECKING IF VALID: " + text);
					Arrays.sort(stopsStringArray);
					if (Arrays.binarySearch(stopsStringArray, text.toString()) > 0) {
						return true;
					}

					return false;

				}

				@Override
				public CharSequence fixText(CharSequence invalidText) {
					Log.v("FIX_TEST", "RETURNING FIXED TEXT");
					return "";
				}
			});
			startPoint.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					Log.v("ON_FOCUS_CHANGE", "Focus changed");
					if (v.getId() == R.id.start_point_et && !hasFocus) {
						Log.v("Test", "Performing validation");
						((AutoCompleteTextView) v).performValidation();
					}
				}
			});

			endPoint.setValidator(new Validator() {

				@Override
				public boolean isValid(CharSequence text) {
					Log.v("IS_VALID_END_POINT", "CHECKING IF VALID: " + text);
					Arrays.sort(stopsStringArray);
					if (Arrays.binarySearch(stopsStringArray, text.toString()) > 0) {
						return true;
					}

					return false;
				}

				@Override
				public CharSequence fixText(CharSequence invalidText) {
					Log.v("FIX_TEST", "RETURNING FIXED TEXT");
					return "";
				}
			});

			endPoint.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (v.getId() == R.id.end_point_et && !hasFocus) {
						((AutoCompleteTextView) v).performValidation();
					}
				}
			});
			startPoint.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String startStopName = (String) parent
							.getItemAtPosition(position);
					Log.i("START_STOP", startStopName);
					Stop fromStopObj = YatayatService
							.findStopByStopName(startStopName);
					// startStopID =
					// YatayatService.findByStopName(startStopName);
					startStopID = fromStopObj.getId();
					startLat = fromStopObj.getLat();
					startLng = fromStopObj.getLng();
					Log.i("START_STOP_ID", startStopID + "");
					Log.i("START_LAT_ID", startLat + "");

				}
			});

			endPoint.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String goalStopName = (String) parent
							.getItemAtPosition(position);
					Log.i("GOAL_STOP", goalStopName);
					// goalStopID = YatayatService.findByStopName(goalStopName);
					Stop goalStopObj = YatayatService
							.findStopByStopName(goalStopName);
					goalStopID = goalStopObj.getId();
					goalLat = goalStopObj.getLat();
					goalLng = goalStopObj.getLng();
				}

			});
			Thread thread = new Thread(null, loadStops);
			thread.start();

			takeMeThere = (Button) findViewById(R.id.find_vehicle_b);
			takeMeThere.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (validateStops()) {
						// Test test = new Test();
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putDouble("startLat", startLat);
						bundle.putDouble("startLng", startLng);
						bundle.putDouble("goalLat", goalLat);
						bundle.putDouble("goalLng", goalLng);

						intent.putExtra("startStopID", startStopID);
						intent.putExtra("goalStopID", goalStopID);
						// intent.putExtra("startLat",
						// String.valueOf(startLat));
						//
						// intent.putExtra("startLng",
						// String.valueOf(startLng));
						//
						// intent.putExtra("goalLat", String.valueOf(goalLat));
						//
						// intent.putExtra("goalLng", String.valueOf(goalLng));

						intent.setClass(YatayatActivity.this,
								RouteListActivity.class);
						intent.putExtras(bundle);
						Log.i("Here WE ARE", "HERE WE ARE");
						startActivity(intent);
						Log.i("Here WE ARE", "HERE WE ARE");
					}
				}

				private boolean validateStops() {
					if (startStopID != 0 && goalStopID != 0) {
						return true;
					}
					return false;
				}
			});
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

		@Override
		public void run() {
			stopsList = YatayatService.getAllStopsList();
			if (stopsList != null) {
				Log.i("STOPLIST_IS_NOT_NULL", "STOPLIST_IS_NOT_NULL");
				Log.i("STOPLIST_SIZE", "" + stopsList.size());
				stopsStringArray = new String[stopsList.size()];
				stopsStringArray = stopsList.toArray(stopsStringArray);
			}
			runOnUiThread(returnResponse);
		}
	};
	private final Runnable returnResponse = new Runnable() {

		@Override
		public void run() {
			Log.i("ENTERING_RETURN_RESPONSE", "ENTERING returnResponse");
			if (stopsList != null && stopsList.size() > 0) {
				Log.i("STOP_TRUE", "IF_TRUE");
				for (int i = 0; i < stopsList.size(); i++) {
					Log.i("ADDING_TO_STOPLIST", "ADDING_TO_STOPLIST");
					arrayAdapter.add(stopsList.get(i));
					Log.i("ADDED_TO_STOPLIST", "ADDED_TO_STOPLIST");
				}
			}

			if (progressSV.getVisibility() == View.VISIBLE) {
				progressSV.setVisibility(View.GONE);
				mainSV.setVisibility(View.VISIBLE);
			}
			arrayAdapter.notifyDataSetChanged();
			// Log.i("NOTIFIED_TO_ADAPTER", "ADDING_TO_ADAPTER");

		}
	};

}