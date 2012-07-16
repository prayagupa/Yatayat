/**
 * 
 */
package com.yatayat.android.widgets;

import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.yatayat.android.models.Stop;

/**
 * @author prayag
 * @created 17 Jul 2012
 * @filename YatayatArrayAdaptor.java
 */
public class StopArrayAdaptor extends ArrayAdapter<Stop> {

	public StopArrayAdaptor(Activity activity, List<Stop> objects) {
		super(activity, android.R.layout.simple_dropdown_item_1line, objects);
	}

}
