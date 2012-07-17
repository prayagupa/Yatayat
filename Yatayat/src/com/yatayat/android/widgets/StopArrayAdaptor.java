/**
 * 
 */
package com.yatayat.android.widgets;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
	List<String> resultsSuggestions;

	private class ViewHolder {
		TextView suggestionText;
	}

	public StopArrayAdaptor(Activity activity, List<Stop> objects) {
		super(activity, R.layout.suggestionlist, objects);
		this.mInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resultsSuggestions = new ArrayList<String>();
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

	@Override
	public Filter getFilter() {
		Filter stopFilter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				for (int i = 0; i < getCount(); i++) {
					if (getItem(i).getName().startsWith(constraint.toString())) {
						Log.i("NAME", getItem(i).getName());

						resultsSuggestions.add(getItem(i).getName());
					}
				}
				Log.i("SUGGESTED RESULT SIZE", "" + resultsSuggestions.size());
				FilterResults filterResults = new FilterResults();
				Log.i("RESULT", "RESULT");
				if (filterResults != null) {
					Log.i(" BEFORE SETTING VALUE", " BEFORE SETTING VALUE");
					filterResults.values = resultsSuggestions;
					Log.i("RESULT VALUE", filterResults.values.toString());
					filterResults.count = resultsSuggestions.size();
				}

				return filterResults;
			}

			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint,
					FilterResults filterResults) {
				clear();
				Log.i("BEFORE CASTING", "BEFORE CASTING");
				ArrayList<Stop> newValues = (ArrayList<Stop>) filterResults.values;
				Log.i("AFTER CASTING", "AFTER CASTING");
				if (newValues != null) {
					for (int i = 0; i < newValues.size(); i++) {
						Log.i("ADDING", "ADDING NEW VALUE");
						add(newValues.get(i));
						Log.i("ADDED", "ADDED NEW VALUE");
					}
					if (filterResults.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			}
		};
		return stopFilter;
	}
}
