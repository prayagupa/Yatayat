package com.yatayat.android;
/**
 * @author         prayag
 * @created_date   15th July 2012 
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;

public class YatayatActivity extends Activity {
	
	private AutoCompleteTextView startPoint, endPoint;
	private ScrollView mainSV, noInternetSV;
	public static String[] places = {"kupondol", "kalimati", "kamaladi", "jawalkhel", "yakunta kuna"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatayat);
        
        //if YES connection
        if(true){
        	mainSV = (ScrollView) findViewById(R.id.main_sv);
        	mainSV.setVisibility(View.VISIBLE);

            startPoint = (AutoCompleteTextView)findViewById(R.id.start_point_et);
            endPoint = (AutoCompleteTextView)findViewById(R.id.end_point_et);
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, places);
            startPoint.setAdapter(adapter);
            endPoint.setAdapter(adapter);
        }else{
        	mainSV = (ScrollView)findViewById(R.id.no_internet_sv);
        	mainSV.setVisibility(View.VISIBLE);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_yatayat, menu);
        return true;
    }
}
