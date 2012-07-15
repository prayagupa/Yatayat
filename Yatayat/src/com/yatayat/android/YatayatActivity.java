package com.yatayat.android;
/**
 * @author         prayag
 * @created_date   15th July 2012 
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MultiAutoCompleteTextView;

public class YatayatActivity extends Activity {
	private MultiAutoCompleteTextView startPoint, endPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatayat);
        
        startPoint = (MultiAutoCompleteTextView)findViewById(R.id.start_point_et);
        endPoint = (MultiAutoCompleteTextView)findViewById(R.id.end_point_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_yatayat, menu);
        return true;
    }

    
}
