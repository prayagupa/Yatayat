package com.yatayat.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;

public class YatayatMapActivity extends MapActivity {
	MapView map;
	double startLat, startLng, goalLat, goalLng;
	final String MODE = "deploy";// test, deploy

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yatayat_map);

		Intent inIntent = getIntent();
		startLat = inIntent.getExtras().getDouble("startLat");
		startLng = inIntent.getExtras().getDouble("startLng");
		goalLat = inIntent.getExtras().getDouble("goalLat");
		goalLng = inIntent.getExtras().getDouble("goalLng");
		Log.i("LIN_LAT", "" + startLat + " " + startLng);

		map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(12);
		map.getController().setCenter(new GeoPoint(startLat, startLng));
		map.setBuiltInZoomControls(true);

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

		line.setTapListener(new LineOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint gp, MapView mapView) {
			}
		});
		this.map.getOverlays().add(line);

	}

	private void showPinOverley() {
		DefaultItemizedOverlay startOverley = new DefaultItemizedOverlay(
				getResources().getDrawable(R.drawable.location_marker));
		DefaultItemizedOverlay endOverley = new DefaultItemizedOverlay(
				getResources().getDrawable(R.drawable.location_marker_end));

		OverlayItem it1 = new OverlayItem(new GeoPoint(startLat, startLng), "",
				"");
		startOverley.addItem(it1);

		OverlayItem it2 = new OverlayItem(new GeoPoint(goalLat, goalLng), "",
				"");
		endOverley.addItem(it2);

		this.map.getOverlays().add(startOverley);
		this.map.getOverlays().add(endOverley);
	}

}