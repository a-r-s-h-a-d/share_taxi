package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class View_shared extends Activity implements OnItemClickListener,JsonResponse {
ListView lv;
String url="";
TextView t;
SharedPreferences sh;
public static String[] driverid,vehid,reqid,flati,flongi,dlati,dlongi,from,to,available_seats,e_res;
public static String driverid1,vid,rqid,flati1,flongi1,dlati1,dlongi1,available_seats1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_shared);
		
        lv=(ListView)findViewById(R.id.listView1);
        t=(TextView)findViewById(R.id.textView2);
        lv.setOnItemClickListener(this);
        
     	JsonReq jr= new JsonReq();
    	jr.json_response=(JsonResponse)View_shared.this;
    	String q="/view_shared_ride?riderid=" + Login.logid+"&lati="+LocationService.lati+"&longi="+LocationService.logi;
    	jr.execute(q);
        
    
    }
   

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sharetoothers, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		rqid=reqid[arg2];
		vid=vehid[arg2];
		flati1=flati[arg2];
		flongi1=flongi[arg2];
		dlati1=dlati[arg2];
		dlongi1=dlongi[arg2];
		driverid1=driverid[arg2];
		available_seats1=available_seats[arg2];
		if(Integer.parseInt(available_seats[arg2])<1)
		{
			Toast.makeText(getApplicationContext(), "sorry booking is completed for this share", Toast.LENGTH_LONG).show();
			startActivity(new Intent(getApplicationContext(),Share_home.class));
		}
		else
		{
			startActivity(new Intent(getApplicationContext(),SHARE_REQ_SENT.class));
		}
	}


public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		
		if(status.equalsIgnoreCase("success"))
		{
			JSONArray ja=(JSONArray)jo.getJSONArray("data");
			 reqid= new String[ja.length()];
		      flati=new String[ja.length()];
		      flongi=new String[ja.length()];
		      dlati=new String[ja.length()];
		      dlongi=new String[ja.length()];
		      vehid=new String[ja.length()];
		      driverid=new String[ja.length()];
		      from= new String[ja.length()];
		      to= new String[ja.length()];
		      available_seats= new String[ja.length()];
			e_res= new String[ja.length()];
			
			for(int i=0;i<ja.length();i++)
			{
				
				reqid[i]=ja.getJSONObject(i).getString("req_id");
				flati[i]=ja.getJSONObject(i).getString("flatitude");
				flongi[i]=ja.getJSONObject(i).getString("flongitude");
				dlati[i]=ja.getJSONObject(i).getString("tlatitude");
				dlongi[i]=ja.getJSONObject(i).getString("tlongitude");
				vehid[i]=ja.getJSONObject(i).getString("vehicle_id");
				driverid[i]=ja.getJSONObject(i).getString("driver_id");
				from[i]=ja.getJSONObject(i).getString("booking_from");
				to[i]=ja.getJSONObject(i).getString("booking_to");
				available_seats[i]=ja.getJSONObject(i).getString("availability");
				
				  e_res[i]="From             :"+from[i]+
                  		"\nTo               :"+to[i]+
                  	    "\nAvailable Seats  :"+available_seats[i];
                  	           
			}
			 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));
		}
		else
		{
			t.setText("currently there is no share taxi ride found");
            Toast.makeText(getApplicationContext(),"nothing to show you",Toast.LENGTH_LONG).show();
		}
	
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
	}
	}
}
