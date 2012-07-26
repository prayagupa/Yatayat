package com.yatayat.android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;

public class YatayatMapActivity extends MapActivity {
	MapView map;
	int startLat, startLng, goalLat, goalLng;
	String routeName, startAddress, endAddress;
	final String MODE = "deploy";// test, deploy

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yatayat_map);

		Bundle bundle = getIntent().getExtras();

		map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(12);
		map.getController().setCenter(new GeoPoint(27.705665, 85.322869));
		map.setBuiltInZoomControls(true);

		/**
 * 
 */
		if (MODE == "test") {
			// startLat = 27.705665;
			// startLng = 85.322869;
			// goalLat = 27.678869;
			// goalLng = 85.349711;

			routeName = "Putalisadak-Koteshwor";
			startAddress = "Putalisadak";
			endAddress = "Koteshwor";
		} else {
			startLat = (int) (bundle.getDouble("startLat") * 1E6);
			startLng = (int) (bundle.getDouble("startLng") * 1E6);
			goalLat = (int) (bundle.getDouble("goalLat") * 1E6);
			goalLng = (int) (bundle.getDouble("goalLng") * 1E6);
			Log.i("startLat", "" + bundle.getDouble("startLat"));
			Log.i("LIN_LAT", "" + startLat + " " + startLng);

			// startLat = (int) (bundle.getDouble("startLat") * 1E6);
			// startLng = (int) (bundle.getDouble("startLng") * 1E6);
			// goalLat = (int) (bundle.getDouble("goalLat") * 1E6);
			// goalLng = (int) (bundle.getDouble("goalLng") * 1E6);
			// routeName = bundle.getString("routeName");
		}

		showPinOverley();
		showRouteOverley();
		map.invalidate();
	}

	// return false since no route is being displayed
	@Override
	public boolean isRouteDisplayed() {
		return false;
	}

	private void showRouteOverley() {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		paint.setAlpha(100);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(5);

		List<GeoPoint> routeData = new ArrayList<GeoPoint>();
		routeData.add(new GeoPoint(startLat, startLng));
		routeData.add(new GeoPoint(goalLat, goalLng));

		LineOverlay line = new LineOverlay(paint);
		line.setData(routeData);
		line.setKey(routeName);

		line.setTapListener(new LineOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint gp, MapView mapView) {
				Toast.makeText(getApplicationContext(), routeName,
						Toast.LENGTH_SHORT).show();
			}
		});
		this.map.getOverlays().add(line);

	}

	private void showPinOverley() {
		DefaultItemizedOverlay startOverley = new DefaultItemizedOverlay(
				getResources().getDrawable(R.drawable.location_marker));
		DefaultItemizedOverlay endOverley = new DefaultItemizedOverlay(
				getResources().getDrawable(R.drawable.location_marker_end));

		OverlayItem it1 = new OverlayItem(new GeoPoint(startLat, startLng),
				startAddress, startAddress);
		startOverley.addItem(it1);

		OverlayItem it2 = new OverlayItem(new GeoPoint(goalLat, goalLng),
				endAddress, endAddress);
		endOverley.addItem(it2);

		this.map.getOverlays().add(startOverley);
		this.map.getOverlays().add(endOverley);
	}

}