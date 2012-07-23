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
import android.widget.Filterable;
import android.widget.TextView;

import com.yatayat.android.R;
import com.yatayat.android.models.Stop;

/**
 * @author prayag
 * @created 17 Jul 2012
 * @lastmodified 20 Jul 2012
 * @filename YatayatArrayAdaptor.java
 */
public class StopArrayAdaptor extends ArrayAdapter<Stop> implements Filterable {
	private final LayoutInflater mLayoutInflater;
	private ArrayList<Stop> mStopSuggestions;

	private class ViewHolder {
		TextView suggestionText;
	}

	public StopArrayAdaptor(Activity activity, List<Stop> stops) {
		super(activity, R.layout.suggestionlist, stops);
		this.mLayoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mStopSuggestions = new ArrayList<Stop>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parents) {
		View itemView = convertView;
		ViewHolder viewHolder = null;
		if (itemView == null) {
			itemView = mLayoutInflater.inflate(R.layout.suggestionlist,
					parents, false);
			viewHolder = new ViewHolder();
			viewHolder.suggestionText = (TextView) itemView
					.findViewById(R.id.suggestionlist_tv);
			itemView.setTag(viewHolder);
		}

		Stop stop = getItem(position);
		viewHolder = (ViewHolder) itemView.getTag();
		viewHolder.suggestionText.setText(stop.getName());
		return itemView;
	}

	@Override
	public Filter getFilter() {
		Filter stopFilter = new Filter() {

			@Override
			public String convertResultToString(Object resultValue) {
				String TAG = "CONVERT_RESULT_TO_STRING";
				Log.i(TAG, "convertResultToString");
				Stop s = (Stop) resultValue;
				return s.getName();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				Log.i("GET_COUNT_ARRAY_ADAPTER", getCount() + "");
				for (int i = 0; i < getCount(); i++) {
					Log.i("NAME_INSIDE_FOR_LOOP", getItem(i).getName());
					if (getItem(i).getName() != null) {
						if (getItem(i)
								.getName()
								.toLowerCase()
								.startsWith(constraint.toString().toLowerCase())) {
							Log.i("NAME", getItem(i).getName());
							if (mStopSuggestions != null)
								mStopSuggestions.add(getItem(i));
							else
								mStopSuggestions = new ArrayList<Stop>();
							// resultsSuggestions.add(getItem(i).getName());
							Log.i("AFTER_ADDING_RESULT_SUGGESTION_" + i,
									"AFTER_ADDING_RESULT_SUGGESTION_" + i);
						}
						Log.i("NOT_NULL_" + i, "NOT_NULL_" + i);
					}

				}
				Log.i("SUGGESTED RESULT SIZE", "" + mStopSuggestions.size());
				FilterResults filterResults = new FilterResults();
				Log.i("RESULT", "RESULT");
				if (filterResults != null) {
					Log.i(" BEFORE SETTING VALUE", " BEFORE SETTING VALUE");
					filterResults.values = mStopSuggestions;
					Log.i("RESULT VALUE", filterResults.values.toString());
					filterResults.count = mStopSuggestions.size();
				}

				return filterResults;
			}

			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint,
					FilterResults filterResults) {
				clear();
				Log.i("BEFORE_CASTING_FILTER_VALUES", "BEFORE CASTING");
				ArrayList<Stop> filteredStops = (ArrayList<Stop>) filterResults.values;
				Log.i("AFTER_CASTING_FILTER_VALUES", "AFTER CASTING");
				if (filteredStops != null) {
					for (int i = 0; i < filteredStops.size(); i++) {
						Log.i("ADDING", "ADDING NEW VALUE");
						add(filteredStops.get(i));
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
