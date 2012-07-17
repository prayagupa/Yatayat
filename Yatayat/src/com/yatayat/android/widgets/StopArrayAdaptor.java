/**
 * 
 */
package com.yatayat.android.widgets;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yatayat.android.R;
import com.yatayat.android.models.Stop;

/**
 * @author prayag
 * @created 17 Jul 2012
 * @filename YatayatArrayAdaptor.java
 */
public class StopArrayAdaptor extends ArrayAdapter<Stop> {
	private final LayoutInflater mInflater;

	private class ViewHolder {
		TextView suggestionText;
	}

	public StopArrayAdaptor(Activity activity, List<Stop> objects) {
		super(activity, R.layout.suggestionlist, objects);
		this.mInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parents) {
		View item = convertView;
		ViewHolder holder = null;
		if (item == null) {
			item = mInflater.inflate(R.layout.suggestionlist, parents, false);
			holder = new ViewHolder();
			holder.suggestionText = (TextView) item
					.findViewById(R.id.suggestionlist_tv);
			item.setTag(holder);
		}

		Stop stop = getItem(position);
		holder = (ViewHolder) item.getTag();
		holder.suggestionText.setText(stop.getName());
		return item;
	}

}
