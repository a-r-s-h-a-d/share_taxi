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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DROP extends Activity implements JsonResponse {
ListView l;
String req_id;
SharedPreferences sh;

String[] reqid,cusname,no_of_pass,e_res;

String soapaction="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop);
		
		l=(ListView)findViewById(R.id.listView1);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)DROP.this;
		String q="/picked_current_ride?driver_id=" + Login.logid;
		jr.execute(q);
		
	
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dro, menu);
		return true;
	}

public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("picked_current_ride"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				reqid=new String[ja.length()];
			     cusname= new String[ja.length()];
			     no_of_pass= new String[ja.length()];   
				e_res= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					reqid[i]=ja.getJSONObject(i).getString("req_id");
					cusname[i]=ja.getJSONObject(i).getString("ride_fname")+" "+ja.getJSONObject(i).getString("rider_lname");
					no_of_pass[i]=ja.getJSONObject(i).getString("noofpassenger");
					
					 e_res[i]= "Customer name           :"+cusname[i]+
                   		   "\nNumber Of Passengers     :"+no_of_pass[i];
                   		     
				}
				 l.setAdapter(new customdroplist(this, reqid, cusname, no_of_pass));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
	}
	}
}
