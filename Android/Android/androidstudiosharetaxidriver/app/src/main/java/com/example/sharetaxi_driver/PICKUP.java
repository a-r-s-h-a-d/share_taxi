package com.example.sharetaxi_driver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PICKUP extends Activity implements JsonResponse {
TextView t1,t2,t3;
String req_id;
String method="";
SharedPreferences sh;

Button b;


String soapaction="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup);
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView4);
		t3=(TextView)findViewById(R.id.textView5);
	    b=(Button)findViewById(R.id.button1);
	    
	    JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)PICKUP.this;
		String q="/current_ride?driver_id=" + Login.logid;
		jr.execute(q);
		
	    b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				  JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)PICKUP.this;
					String q="/pickuser?req_id=" + req_id;
					jr.execute(q);
					
			
			}
		});
		
	}

public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("current_ride"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
			
				
					req_id=ja.getJSONObject(0).getString("req_id");
					 t1.setText(ja.getJSONObject(0).getString("ride_fname")+" "+ja.getJSONObject(0).getString("rider_lname"));   
	                 t2.setText(ja.getJSONObject(0).getString("booking_from"));
	                 t3.setText(ja.getJSONObject(0).getString("booking_to"));  
				
				 
			}
			else
			{
				 Toast.makeText(getApplicationContext(), "no current ride here ", Toast.LENGTH_LONG).show();
            	 startActivity(new Intent(getApplicationContext(),HOME.class));
			}
		}
		else if(method.equalsIgnoreCase("pickuser"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "pick up user successfully ", Toast.LENGTH_LONG).show();
					
            	 startActivity(new Intent(getApplicationContext(),HOME.class));
			}
			else
			{
				 Toast.makeText(getApplicationContext(), "something went wrong please check your connection ", Toast.LENGTH_LONG).show();
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picku, menu);
		return true;
	}

}
