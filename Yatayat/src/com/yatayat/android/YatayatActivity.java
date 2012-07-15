package com.yatayat.android;
/**
 * @author         prayag
 * @created_date   15th July 2012 
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

public class YatayatActivity extends Activity {
	
	private MultiAutoCompleteTextView startPoint, endPoint;
	public static String[] places = {"kupondol", "kalimati", "kamaladi", "jawalkhel", "yakunta kuna"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatayat);
        
        startPoint = (MultiAutoCompleteTextView)findViewById(R.id.start_point_et);
        endPoint = (MultiAutoCompleteTextView)findViewById(R.id.end_point_et);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, places);
        startPoint.setAdapter(adapter);
        startPoint.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        endPoint.setAdapter(adapter);
        endPoint.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_yatayat, menu);
        return true;
    }

    
}
