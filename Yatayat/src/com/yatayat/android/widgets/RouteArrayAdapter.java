package com.yatayat.android.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yatayat.android.R;
import com.yatayat.android.models.Route;

public class RouteArrayAdapter extends ArrayAdapter<Route> {
	private final LayoutInflater mInflater;
	ArrayList<Route> avilableRoute;

	public RouteArrayAdapter(Context context, List<Route> objects) {
		super(context, R.layout.vehicle_route_detail, objects);
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		avilableRoute = new ArrayList<Route>();
		// TODO Auto-generated constructor stub
	}

	private class ViewHolder {
		TextView route;
		ImageView image;
		TextView ref;
		TextView transport;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parents) {
		View item = convertView;
		ViewHolder holder = null;

		if (item == null) {
			item = mInflater.inflate(R.layout.vehicle_route_detail, parents,
					false);
			holder = new ViewHolder();
			holder.route = (TextView) item.findViewById(R.id.vehicle_route_tv);
			holder.ref = (TextView) item.findViewById(R.id.vehicle_ref_tv);
			holder.transport = (TextView) item
					.findViewById(R.id.vehicle_transport_tv);
			item.setTag(holder);
		}

		Route route = getItem(position);
		holder = (ViewHolder) item.getTag();
		holder.route.setText(route.getName());
		holder.ref.setText(route.getVehicle().getRef());
		holder.transport.setText(route.getVehicle().getTransport());
		return item;
	}
}
