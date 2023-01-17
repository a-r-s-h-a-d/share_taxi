package com.example.sharetaxi;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD) public class RATE extends Activity implements JsonResponse{
	
	SharedPreferences sh;
	RatingBar r;
	Button b;
	
	float rate;
	TextView t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate);
		
			r=(RatingBar)findViewById(R.id.ratingBar1);
			b=(Button)findViewById(R.id.button1);
			b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					rate=r.getRating();
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)RATE.this;
					String q="/addrating?reqid=" + Add_rating.rid+"&rating="+rate+"";
					jr.execute(q);
					
					
				}
			});
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rate, menu);
		return true;
	}
	@Override
	public void response(JSONObject jo) 
	{
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "success ", Toast.LENGTH_LONG).show();
            	 startActivity(new Intent(getApplicationContext(),Home.class));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
