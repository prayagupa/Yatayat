package com.yatayat.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AutoCompleteTextView;

public class YatayatActivity extends Activity {
	private AutoCompleteTextView startPoint, endPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatayat);
        
        startPoint = (AutoCompleteTextView)findViewById(R.id.start_point_et);
        endPoint = (AutoCompleteTextView)findViewById(R.id.end_point_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_yatayat, menu);
        return true;
    }

    
}
